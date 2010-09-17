package com.skzs.multiproperties.ui.editors;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IFindReplaceTarget;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.skzs.multiproperties.base.model.AbstractRecord;
import com.skzs.multiproperties.base.model.PropertyRecord;
import com.skzs.multiproperties.base.model.Table;
import com.skzs.multiproperties.base.model.listener.IRecordChangeListener;
import com.skzs.multiproperties.base.model.listener.IStructuralChangeListener;
import com.skzs.multiproperties.ui.Activator;

public class Editor extends FormEditor implements IResourceChangeListener, IRecordChangeListener, IStructuralChangeListener
{

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

	public EditorContributor getContributor()
	{
		return (EditorContributor) getEditorSite().getActionBarContributor();
	}

	public OutlinePage getOutlinePage()
	{
		return fOutlinePage;
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException
	{
		setSite(site);
		setInput(input);
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
			e.printStackTrace();
		}
	}

	@Override
	protected void addPages()
	{
		try
		{
			addPage(new OverviewPage(this), getEditorInput());
			addPage(new ColumnsPage(this), getEditorInput());
			addPage(new TablePage(this), getEditorInput());
			final IPreferenceStore store = Activator.getDefault().getPreferenceStore();
			getContributor().setActiveEditor(this);
			setActivePage(store.getInt("initial_page"));
		}
		catch (final PartInitException e)
		{
			e.printStackTrace();
		}
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
	@SuppressWarnings("unchecked")
	public Object getAdapter(final Class adapter)
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
