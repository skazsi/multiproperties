package hu.skzs.multiproperties.ui.editor;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IFindReplaceTarget;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.part.MultiPageSelectionProvider;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import hu.skzs.multiproperties.base.api.IHandler;
import hu.skzs.multiproperties.base.io.EditorInputAdapter;
import hu.skzs.multiproperties.base.io.EditorInputAdapterFactory;
import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.base.model.fileformat.ISchemaConverter;
import hu.skzs.multiproperties.base.model.fileformat.SchemaConverterFactory;
import hu.skzs.multiproperties.base.model.fileformat.UnsupportedSchemaVersionException;
import hu.skzs.multiproperties.base.model.listener.IRecordChangeListener;
import hu.skzs.multiproperties.base.model.listener.IStructuralChangeListener;
import hu.skzs.multiproperties.base.registry.element.HandlerRegistryElement;
import hu.skzs.multiproperties.support.WorkbenchTypeProvider;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.Messages;
import hu.skzs.multiproperties.ui.preferences.PreferenceConstants;
import hu.skzs.multiproperties.ui.util.ErrorDialogWithStackTrace;

public class Editor extends MultiPageEditorPart
		implements IResourceChangeListener, IRecordChangeListener, IStructuralChangeListener {

	/**
	 * The <code>ID</code> represents the MultiProperties Editor identifier. It is
	 * the same value than how the editor is specified in the
	 * <code>plugin.xml</code>.
	 */
	public static final String ID = "hu.skzs.multiproperties.ui.editor";

	/**
	 * The <code>SCHEMA_CHARSET</code> specifies the used encoding for the
	 * MultiProperties file.
	 */
	public static final String SCHEMA_CHARSET = "UTF-8";

	private EditorInputAdapter<?> editorInputAdapter;
	private long modificationStamp = -1;

	private OverviewPage overviewPage;
	private ColumnsPage columnsPage;
	private TablePage tablePage;
	private Table table;
	private final List<AbstractRecord> vecClipboard = new LinkedList<AbstractRecord>();
	private IFindReplaceTarget fFindReplaceTarget;
	private OutlinePage outlinePage;

	public Editor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	@Override
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}

	public void resourceChanged(final IResourceChangeEvent event) {
		if (event.getType() == IResourceChangeEvent.PRE_CLOSE) {
			Display.getDefault().asyncExec(new Runnable() {

				public void run() {
					final IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
					for (int i = 0; i < pages.length; i++) {
						if (Editor.this.getEditorInput().getFile().getProject().equals(event.getResource())) {
							final IEditorPart editorPart = pages[i].findEditor(Editor.this.getEditorInput());
							pages[i].closeEditor(editorPart, true);
						}
					}
				}
			});

		} else if (event.getType() == IResourceChangeEvent.POST_CHANGE) {
			Display.getDefault().asyncExec(new Runnable() {

				public void run() {
					try {
						final IResourceDelta resourceDelta = event.getDelta();
						if (resourceDelta != null) {
							final IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
								public boolean visit(final IResourceDelta delta) {
									if (delta.getKind() != IResourceDelta.CHANGED)
										return true;
									if ((delta.getFlags() & IResourceDelta.CONTENT) == 0)
										return true;
									final IResource resource = delta.getResource();
									if (resource.equals(Editor.this.getEditorInput().getFile())
											&& getEditorInput().getFile().getModificationStamp() != modificationStamp) {
										final String message = Messages.getString("editor.filechanged.confirm",
												resource.getFullPath());

										if (MessageDialog.open(MessageDialog.QUESTION, null,
												Messages.getString("editor.filechanged.title"), message, SWT.NONE,
												new String[] { Messages.getString("editor.filechanged.button.replace"),
														Messages.getString(
																"editor.filechanged.button.dontreplace") }) == 0) {

											try {
												doLoad();
												overviewPage.refresh();
												columnsPage.refresh();
												tablePage.refresh();
												if (outlinePage != null) {
													outlinePage.setTable(table);
												}

											} catch (final Exception e) {
												Activator.logError(e);
												throw new RuntimeException(e);
											}
										}
									}
									return true;
								}
							};
							resourceDelta.accept(visitor);
						}
					} catch (final CoreException e) {
						Activator.logError(e);
					}
				}
			});
		}
	}

	/**
	 * Returns the current {@link Table} instance.
	 * <p>
	 * The instance can be changed during life cycle of an editor instance, because
	 * when an external modification occurs, a new {@link Table} instance is built.
	 * Thus clients should not hold a reference to this returned instance.
	 * </p>
	 *
	 * @return the current {@link Table} instance
	 */
	public Table getTable() {
		return table;
	}

	@Override
	public FileEditorInput getEditorInput() {
		return (FileEditorInput) super.getEditorInput();
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		super.init(site, input);
		try {
			editorInputAdapter = EditorInputAdapterFactory.getInstance().getEditorInputAdapter(getEditorInput());
			setPartName(editorInputAdapter.getName());
			doLoad();
		} catch (final Throwable e) {
			throw new PartInitException("Unable to initialize the editor", e);
		}
	}

	@Override
	protected void createPages() {
		try {
			// Overview page
			overviewPage = new OverviewPage();
			overviewPage.setEditor(this);
			addPage(overviewPage, getEditorInput());
			setPageText(getPageCount() - 1, overviewPage.getPageText());

			// Overview page
			columnsPage = new ColumnsPage();
			columnsPage.setEditor(this);
			addPage(columnsPage, getEditorInput());
			setPageText(getPageCount() - 1, columnsPage.getPageText());

			// Table page
			tablePage = new TablePage();
			tablePage.setEditor(this);
			addPage(tablePage, getEditorInput());
			setPageText(getPageCount() - 1, tablePage.getPageText());

			// Selecting the initial page specified by the preferences
			setActivePage(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.INITIAL_PAGE));
		} catch (final PartInitException e) {
			Activator.logError(e);
		}
	}

	/**
	 * Change the active page to that page which has the given identifier.
	 * <p>
	 * The method does nothing if no editor page can be found with the given
	 * identifier.
	 * </p>
	 *
	 * @param id the given identifier
	 * @see MPEditorPage#setId(String)
	 * @see MPEditorPage#getId()
	 */
	public void setActivePage(final String id) {
		for (int i = 0; i < getPageCount(); i++) {
			final MPEditorPage editorPage = (MPEditorPage) getEditor(i);
			if (editorPage.getId().equals(id))
				setActivePage(i);
		}
	}

	@Override
	protected void pageChange(final int newPageIndex) {
		// Notifying the selected page that it has been activated
		final MPEditorPage editorPage = (MPEditorPage) getActiveEditor();
		editorPage.setActive();

		// FIXME: check the super implementation
		final MultiPageSelectionProvider provider = (MultiPageSelectionProvider) getSite().getSelectionProvider();
		final SelectionChangedEvent event = new SelectionChangedEvent(provider, provider.getSelection());
		provider.fireSelectionChanged(event);
		provider.firePostSelectionChanged(event);

		super.pageChange(newPageIndex);
	}

	@Override
	public boolean isDirty() {
		return table.isDirty();
	}

	/**
	 * Discards the previously loaded and constructed {@link Table} instance if
	 * there were any, then constructs a new instance based on the
	 * {@link #getFile()}.
	 * <p>
	 * The method performs the followings:
	 * </p>
	 * <ol>
	 * <li>Constructs a {@link ISchemaConverter} based on the
	 * {@link #getFile()}</li>
	 * <li>Constructs a new {@link Table} based on the {@link ISchemaConverter},
	 * thus the previous instance is discarded</li>
	 * <li>Sets the {@link IStructuralChangeListener} and
	 * {@link IRecordChangeListener} of the {@link Table} to this editor
	 * instance</li>
	 * <li>Sets the <code>dirty</code> flag of the table to <code>false</code></li>
	 * </ol>
	 *
	 * @throws Exception
	 * @see {@link #getFile()}
	 */
	private void doLoad() throws Exception {
		try {
			final byte[] content = editorInputAdapter.getFileContentAccessor().getContent();
			final ISchemaConverter schemaConverter = SchemaConverterFactory.getSchemaConverter(content);
			table = schemaConverter.convert(content);
			table.setStructuralChangeListener(this);
			table.setRecordChangeListener(this);
			table.setDirty(false);

			modificationStamp = getEditorInput().getFile().getModificationStamp();

			// Checking the file format version
			if (!schemaConverter.getVersion().equals(SchemaConverterFactory.NEWEST_VERSION)) {
				Display.getCurrent().asyncExec(new Runnable() {

					public void run() {
						if (MessageDialog.openConfirm(getSite().getShell(), Messages.getString("general.confirm.title"),
								Messages.getString("editor.error.obsoleted"))) {
							table.setVersion(SchemaConverterFactory.NEWEST_VERSION);
						}
					}
				});
			}
		} catch (final UnsupportedSchemaVersionException e) {
			MessageDialog.openError(getSite().getShell(), Messages.getString("general.error.title"),
					Messages.getString("editor.error.unsupported"));
			throw e;
		}
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		// Saving the content
		try {
			final ISchemaConverter schemaConverter = SchemaConverterFactory.getSchemaConverter(table);
			final byte[] content = schemaConverter.convert(table);
			editorInputAdapter.getFileContentAccessor().setContent(content);
			modificationStamp = getEditorInput().getFile().getModificationStamp();
			table.setDirty(false);
		} catch (final Exception e) {
			Activator.logError("Unexpected schema converter error occurred during saving content", e);
			ErrorDialogWithStackTrace.openError(getSite().getShell(), Messages.getString("editor.error.file.save"), e);
			return;
		}

		if (WorkbenchTypeProvider.isStandAlone()) // TODO: Handlers are not supported in stand-alone
		{
			return;
		}

		// Saving the columns with the configured handler
		try {
			if (!table.getHandler().equals("")) {
				// Getting the handler element
				final HandlerRegistryElement element = Activator.getDefault().getHandlerRegistry()
						.getElementByName(table.getHandler());
				if (element == null) {
					MessageDialog.openError(null, Messages.getString("general.error.title"),
							Messages.getString("editor.error.file.save.unknown"));
				} else {
					// Invoking the handler saving
					final IHandler handler = element.getHandler();
					for (int i = 0; i < table.getColumns().size(); i++) {
						if (!table.getColumns().get(i).getHandlerConfiguration().equals("")) {
							try {
								handler.save(table.getColumns().get(i).getHandlerConfiguration(), table,
										table.getColumns().get(i));
							} catch (final Exception e) {
								Activator.logError(e);
								ErrorDialogWithStackTrace.openError(getSite().getShell(),
										Messages.getString("editor.error.handler.save"), e);
							}
						}
					}
				}

			}
		} catch (final Exception e) {
			Activator.logError("Unexpected error occurred during saving content by handler", e);
		}

		// Setting the markers
		try {
			int duplicated = 0;
			for (int i = 0; i < table.size(); i++) {
				if (!(table.get(i) instanceof PropertyRecord))
					continue;
				if (((PropertyRecord) table.get(i)).isDuplicated())
					duplicated++;
			}
			if (duplicated > 0) {
				editorInputAdapter.setMarker(Messages.getString("editor.marker.error.prefix") + duplicated
						+ Messages.getString("editor.marker.error.suffix"));
			} else {
				editorInputAdapter.setMarker(null);
			}
		} catch (final Exception e) {
			Activator.logError("Unexpected error occurred during setting markers", e);
		}
	}

	@Override
	public void doSaveAs() {
		// Do nothing
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	public List<AbstractRecord> getClipboard() {
		return vecClipboard;
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (IFindReplaceTarget.class.equals(adapter)) {
			if (fFindReplaceTarget == null)
				fFindReplaceTarget = new FindReplaceTarget(this);
			return adapter.cast(fFindReplaceTarget);
		}
		if (IContentOutlinePage.class.equals(adapter)) {
			if (outlinePage == null)
				outlinePage = new OutlinePage(table);
			return adapter.cast(outlinePage);
		}
		return super.getAdapter(adapter);
	}

	public void changed(final AbstractRecord record) {
		if (tablePage != null) {
			final TableViewer tableViewer = tablePage.getTableViewer();
			if (tableViewer != null)
				tableViewer.refresh(record);
		}
		firePropertyChange(IEditorPart.PROP_DIRTY);
		refreshOutline();
	}

	public void changed() {
		{
			if (tablePage != null) {
				final TableViewer tableViewer = tablePage.getTableViewer();
				if (tableViewer != null)
					tableViewer.refresh();
			}
		}
		firePropertyChange(IEditorPart.PROP_DIRTY);
		refreshOutline();
	}

	private void refreshOutline() {
		if (outlinePage != null) {
			outlinePage.selectionChanged(this,
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection());
		}
	}
}
