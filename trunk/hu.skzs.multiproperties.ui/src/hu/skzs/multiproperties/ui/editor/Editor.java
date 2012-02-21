package hu.skzs.multiproperties.ui.editor;

import hu.skzs.multiproperties.base.api.IHandler;
import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.base.model.fileformat.ISchemaConverter;
import hu.skzs.multiproperties.base.model.fileformat.SchemaConverterException;
import hu.skzs.multiproperties.base.model.fileformat.SchemaConverterFactory;
import hu.skzs.multiproperties.base.model.fileformat.UnsupportedSchemaVersionException;
import hu.skzs.multiproperties.base.model.listener.IRecordChangeListener;
import hu.skzs.multiproperties.base.model.listener.IStructuralChangeListener;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.Messages;
import hu.skzs.multiproperties.ui.preferences.PreferenceConstants;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IFindReplaceTarget;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.part.MultiPageSelectionProvider;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class Editor extends MultiPageEditorPart implements IResourceChangeListener, IRecordChangeListener,
		IStructuralChangeListener
{

	/**
	 * The <code>ID</code> represents the MultiProperties Editor identifier. It is the same
	 * value than how the editor is specified in the <code>plugin.xml</code>.
	 */
	public static final String ID = "hu.skzs.multiproperties.ui.editor"; //$NON-NLS-1$

	private Table table;
	private final List<AbstractRecord> vecClipboard = new LinkedList<AbstractRecord>();
	private IFindReplaceTarget fFindReplaceTarget;
	private OutlinePage fOutlinePage;

	public Editor()
	{
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	/**
	 * Closes all project files on project close.
	 */
	public void resourceChanged(final IResourceChangeEvent event)
	{
		if (event.getType() == IResourceChangeEvent.PRE_CLOSE)
		{
			Display.getDefault().asyncExec(new Runnable()
			{

				public void run()
				{
					final IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
					for (int i = 0; i < pages.length; i++)
					{
						if (((FileEditorInput) Editor.this.getEditorInput()).getFile().getProject()
								.equals(event.getResource()))
						{
							final IEditorPart editorPart = pages[i].findEditor(Editor.this.getEditorInput());
							pages[i].closeEditor(editorPart, true);
						}
					}
				}
			});
		}
	}

	/**
	 * Returns the {@link IFile} instance. That file stores the MultiProperties content.
	 * @return the {@link IFile} instance
	 */
	public IFile getFile()
	{
		IFileEditorInput fileEditorInput = (IFileEditorInput) getEditorInput();
		return fileEditorInput.getFile();
	}

	/**
	 * Returns the current {@link Table} instance.
	 * <p>The instance can be changed during life cycle of an editor instance, because when an external modification occurs, a new {@link Table} instance is built.
	 * Thus clients should not hold a reference to this returned instance.</p>
	 * @return the current {@link Table} instance
	 */
	public Table getTable()
	{
		return table;
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException
	{
		super.init(site, input);
		try
		{
			setPartName(getFile().getName());
			doLoad();
		}
		catch (final Throwable e)
		{
			throw new PartInitException("Unable to initialize the editor", e); //$NON-NLS-1$
		}
	}

	@Override
	protected void createPages()
	{
		try
		{
			// Overview page
			MPEditorPage overviewPage = new OverviewPage();
			overviewPage.setEditor(this);
			addPage(overviewPage, getEditorInput());
			setPageText(getPageCount() - 1, overviewPage.getPageText());

			// Overview page
			MPEditorPage columnsPage = new ColumnsPage();
			columnsPage.setEditor(this);
			addPage(columnsPage, getEditorInput());
			setPageText(getPageCount() - 1, columnsPage.getPageText());

			// Table page
			MPEditorPage tablePage = new TablePage();
			tablePage.setEditor(this);
			addPage(tablePage, getEditorInput());
			setPageText(getPageCount() - 1, tablePage.getPageText());

			// Selecting the initial page specified by the preferences
			setActivePage(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.INITIAL_PAGE));
		}
		catch (PartInitException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Change the active page to that page which has the given identifier.
	 * <p>The method does nothing if no editor page can be found with the given identifier.</p>
	 * @param id the given identifier
	 * @see MPEditorPage#setId(String)
	 * @see MPEditorPage#getId()
	 */
	public void setActivePage(String id)
	{
		for (int i = 0; i < getPageCount(); i++)
		{
			MPEditorPage editorPage = (MPEditorPage) getEditor(i);
			if (editorPage.getId().equals(id))
				setActivePage(i);
		}
	}

	@Override
	protected void pageChange(int newPageIndex)
	{
		// FIXME: check the super implementation
		MultiPageSelectionProvider provider = (MultiPageSelectionProvider) getSite().getSelectionProvider();
		SelectionChangedEvent event = new SelectionChangedEvent(provider, provider.getSelection());
		provider.fireSelectionChanged(event);
		provider.firePostSelectionChanged(event);

		// Notifying the selected page that it has been activated 
		MPEditorPage editorPage = (MPEditorPage) getActiveEditor();
		editorPage.setActive();

		super.pageChange(newPageIndex);
	}

	@Override
	public boolean isDirty()
	{
		return table.isDirty();
	}

	/**
	 * Discards the previously loaded and constructed {@link Table} instance if there were any, then constructs a new instance based on the {@link #getFile()}.
	 * <p>The method performs the followings:</p>
	 * <ol>
	 * <li>Constructs a {@link ISchemaConverter} based on the {@link #getFile()}</li>
	 * <li>Constructs a new {@link Table} based on the {@link ISchemaConverter()}, thus the previous instance is discarded</li>
	 * <li>Sets the {@link IStructuralChangeListener} and {@link IRecordChangeListener} of the {@link Table} to this editor instance</li>
	 * <li>Sets the <code>dirty</code> flag of the table to <code>false</code></li>
	 * </ol>
	 * @throws SchemaConverterException
	 * @see {@link #getFile()}
	 */
	private void doLoad() throws SchemaConverterException
	{
		try
		{
			ISchemaConverter schemaConverter = SchemaConverterFactory.getSchemaConverter(getFile());
			table = schemaConverter.convert(getFile());
			table.setStructuralChangeListener(this);
			table.setRecordChangeListener(this);
			table.setDirty(false);
		}
		catch (UnsupportedSchemaVersionException e)
		{
			Activator.logError("Unexpected error occurred during loading content", e); //$NON-NLS-1$
			MessageDialog.openError(getSite().getShell(), title, message);
			throw e;
		}
		catch (SchemaConverterException e)
		{
			Activator.logError("Unexpected error occurred during loading content", e); //$NON-NLS-1$
			throw e;
		}
	}

	@Override
	public void doSave(final IProgressMonitor monitor)
	{
		IFile file = getFile();

		// Saving the content
		try
		{
			ISchemaConverter schemaConverter = SchemaConverterFactory.getSchemaConverter(table);
			schemaConverter.convert(file, table);
			table.setDirty(false);
		}
		catch (final SchemaConverterException e)
		{
			Activator.logError("Unexpected error occurred during saving content", e); //$NON-NLS-1$
		}

		// Saving the columns with the configured handler
		try
		{
			if (!table.getHandler().equals(""))
			{
				IConfigurationElement element = null;
				final IExtensionRegistry reg = Platform.getExtensionRegistry();
				final IConfigurationElement[] extensions = reg
						.getConfigurationElementsFor("hu.skzs.multiproperties.handler"); //$NON-NLS-1$
				for (int i = 0; i < extensions.length; i++)
				{
					if (extensions[i].getAttribute("name").equals(table.getHandler()))
					{
						element = extensions[i];
						break;
					}
				}
				final IHandler handler = (IHandler) element.createExecutableExtension("class");
				for (int i = 0; i < table.getColumns().size(); i++)
				{
					if (!table.getColumns().get(i).getHandlerConfiguration().equals(""))
					{
						try
						{
							handler.save(table.getColumns().get(i).getHandlerConfiguration(), table, table.getColumns()
									.get(i));
						}
						catch (final CoreException e)
						{
							MessageDialog.openError(null, "Error", e.getMessage());
							Activator.log(e.getStatus());
						}
					}
				}
			}
		}
		catch (CoreException e)
		{
			Activator.logError("Unexpected error occurred during saving content by handler", e); //$NON-NLS-1$
		}

		// Setting the markers
		try
		{
			int duplicated = 0;
			for (int i = 0; i < table.size(); i++)
			{
				if (!(table.get(i) instanceof PropertyRecord))
					continue;
				if (((PropertyRecord) table.get(i)).isDuplicated())
					duplicated++;
			}
			file.deleteMarkers(null, true, 0);
			if (duplicated > 0)
			{
				IMarker marker = file.findMarker(0);
				if (marker == null)
				{
					marker = file.createMarker(IMarker.PROBLEM);
					marker.setAttribute(
							IMarker.MESSAGE,
							Messages.getString("editor.marker.error.prefix") + duplicated + Messages.getString("editor.marker.error.suffix")); //$NON-NLS-1$ //$NON-NLS-2$
					marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
				}
			}
		}
		catch (final Throwable e)
		{
			Activator.logError("Unexpected error occurred during setting markers", e); //$NON-NLS-1$
		}
	}

	@Override
	public void doSaveAs()
	{
		// Do nothing
	}

	@Override
	public boolean isSaveAsAllowed()
	{
		return false;
	}

	public List<AbstractRecord> getClipboard()
	{
		return vecClipboard;
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") final Class adapter)
	{
		if (IFindReplaceTarget.class.equals(adapter))
		{
			if (fFindReplaceTarget == null)
				fFindReplaceTarget = new FindReplaceTarget(this);
			return fFindReplaceTarget;
		}
		if (IContentOutlinePage.class.equals(adapter))
		{
			if (fOutlinePage == null)
				fOutlinePage = new OutlinePage();
			return fOutlinePage;
		}
		return super.getAdapter(adapter);
	}

	public void changed(final AbstractRecord record)
	{
		if (getActiveEditor() instanceof TablePage)
		{
			final TablePage tablePage = (TablePage) getActiveEditor();
			tablePage.getTableViewer().refresh(record);
		}
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	public void changed()
	{
		if (getActiveEditor() instanceof TablePage)
		{
			final TablePage tablePage = (TablePage) getActiveEditor();
			tablePage.getTableViewer().refresh();
		}
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}
}
