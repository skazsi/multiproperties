package hu.skzs.multiproperties.ui.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

/**
 * The <code>MPEditorPage</code> class is the base implementation for MultiProperties Editor pages.
 * @author sallai
 */
public abstract class MPEditorPage extends EditorPart
{

	/**
	 * Identifier of the editor page
	 */
	protected String id;

	/**
	 * Instance of MultiProperties Editor
	 */
	protected Editor editor;

	/**
	 * Sets the identifier of the page.
	 * @param id the given identifier
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * Returns the identifier of the page.
	 * @return the identifier of the page
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * Sets the instance of MultiProperties Editor.
	 * <p>Client must set it immediately after constructing the editor page.</p>
	 * @param editor the given instance of MultiProperties Editor
	 */
	public void setEditor(final Editor editor)
	{
		this.editor = editor;
	}

	/**
	 * Returns the instance of MultiProperties Editor.
	 * @return the instance of MultiProperties Editor
	 */
	public Editor getEditor()
	{
		return editor;
	}

	/**
	 * Returns the page text.
	 * @return the page text
	 */
	public abstract String getPageText();

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException
	{
		setSite(site);
		setInput(input);
	}

	/**
	 * Notifies this page of MultiProperties Editor that the page has been activated.
	 * This method is called when the user selects this tab.
	 */
	public abstract void setActive();

	/**
	 * Deprecated method and does nothing when invoked.
	 */
	@Override
	public final void doSave(IProgressMonitor monitor)
	{
	}

	/**
	 * Deprecated method and does nothing when invoked.
	 */
	@Override
	public final void doSaveAs()
	{
	}

	/**
	 * Deprecated method and does nothing when invoked.
	 */
	@Override
	public final boolean isDirty()
	{
		return false;
	}

	/**
	 * Deprecated method and does nothing when invoked.
	 */
	@Override
	public final boolean isSaveAsAllowed()
	{
		return false;
	}

	@Override
	public void setFocus()
	{
	}
}
