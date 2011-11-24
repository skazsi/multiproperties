package hu.skzs.multiproperties.ui.editor;

import hu.skzs.multiproperties.base.model.AbstractRecord;

import org.eclipse.jface.text.IFindReplaceTarget;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.graphics.Point;

/**
 * The <code>FindReplaceTarget</code> defines target for finding {@link AbstractRecord}
 * instances in the table page.
 * <p>The primary use of this class is to support the standard Eclipse based find and replace command.</p>
 * @author sallai
 */
public class FindReplaceTarget implements IFindReplaceTarget
{

	private final Editor editor;

	/**
	 * Default constructor.
	 * @param editor the given {@link Editor} instance
	 */
	public FindReplaceTarget(Editor editor)
	{
		this.editor = editor;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.text.IFindReplaceTarget#canPerformFind()
	 */
	public boolean canPerformFind()
	{
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.text.IFindReplaceTarget#findAndSelect(int, java.lang.String, boolean, boolean, boolean)
	 */
	public int findAndSelect(int widgetOffset, String findString, boolean searchForward, boolean caseSensitive, boolean wholeWord)
	{
		if (!(editor.getSelectedPage() instanceof TablePage))
			return -1;
		int position = widgetOffset;
		while ((searchForward && position < editor.getTable().size() - 1) || (!searchForward && position > 0))
		{
			if (searchForward)
				position++;
			if (editor.getTable().find(position, findString, caseSensitive, wholeWord))
			{
				TablePage tablepage = (TablePage) editor.getSelectedPage();
				TableViewer tableviewer = tablepage.getTableViewer();
				tableviewer.getTable().setSelection(position);
				//				if (editor.getOutlinePage() != null)
				//					editor.getOutlinePage().update(editor.getTable().get(position));
				return position;
			}
			if (!searchForward)
				position--;
		}
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.text.IFindReplaceTarget#getSelection()
	 */
	public Point getSelection()
	{
		if (!(editor.getSelectedPage() instanceof TablePage))
			return new Point(-1, 0);
		TablePage tablepage = (TablePage) editor.getSelectedPage();
		TableViewer tableviewer = tablepage.getTableViewer();
		ISelection selection = tableviewer.getSelection();
		if (selection.isEmpty())
			return new Point(-1, 0);
		AbstractRecord record = (AbstractRecord) ((IStructuredSelection) selection).getFirstElement();
		return new Point(editor.getTable().indexOf(record), 0);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.text.IFindReplaceTarget#getSelectionText()
	 */
	public String getSelectionText()
	{
		return ""; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.text.IFindReplaceTarget#isEditable()
	 */
	public boolean isEditable()
	{
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.text.IFindReplaceTarget#replaceSelection(java.lang.String)
	 */
	public void replaceSelection(String text)
	{
	}
}
