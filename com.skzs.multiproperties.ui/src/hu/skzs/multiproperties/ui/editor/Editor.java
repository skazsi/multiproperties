package hu.skzs.multiproperties.ui.editor;

import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.base.model.listener.IRecordChangeListener;
import hu.skzs.multiproperties.base.model.listener.IStructuralChangeListener;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.preferences.PreferenceConstants;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
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

public class Editor extends MultiPageEditorPart implements IResourceChangeListener, IRecordChangeListener, IStructuralChangeListener
{

	/**
	 * The <code>ID</code> represents the MultiProperties Editor identifier. It is the same
	 * value than how the editor is specified in the <code>plugin.xml</code>.
	 */
	public static final String ID = "hu.skzs.multiproperties.ui.editor"; //$NON-NLS-1$

	private Table table;
	private IFile file;
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
			Display.getDefault().asyncExec(new Runnable() {

				public void run()
				{
					final IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
					for (int i = 0; i < pages.length; i++)
					{
						if (((FileEditorInput) Editor.this.getEditorInput()).getFile().getProject().equals(event.getResource()))
						{
							final IEditorPart editorPart = pages[i].findEditor(Editor.this.getEditorInput());
							pages[i].closeEditor(editorPart, true);
						}
					}
				}
			});
		}
	}

	public IFile getFile()
	{
		return file;
	}

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
			final IFileEditorInput fileeditorinput = (IFileEditorInput) input;
			file = fileeditorinput.getFile();
			table = new Table();
			table.load(file);
			table.setStructuralChangeListener(this);
			table.setRecordChangeListener(this);
			setPartName(file.getName());
		}
		catch (final Throwable e)
		{
			throw new PartInitException("Unable to initialize the editor", e);
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

	@Override
	public void doSave(final IProgressMonitor monitor)
	{
		try
		{
			final IFileEditorInput fileeditorinput = (IFileEditorInput) getEditorInput();
			file = fileeditorinput.getFile();
			table.save(file);

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
					marker.setAttribute(IMarker.MESSAGE, "There are " + duplicated + " duplicated records");
					marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
				}
			}
		}
		catch (final Throwable e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void doSaveAs()
	{
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
				fOutlinePage = new OutlinePage(this);
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
