package hu.skzs.multiproperties.ui.propertytester;

import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.ui.editor.Editor;

import java.util.Iterator;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * The <code>MPEPropertyTester</code> property tester provides several condition checking to
 * help identifying which commands can be enabled. The <code>plugin.xml</code> use it.
 * <p>Available properties are the followings.</p>
 * <ul>
 * <li><code>isContinuousSelection</code> - check whether the current selection is continuous of {@link AbstractRecord} or not</li>
 * <li><code>isBeginningSelection</code> - check whether the current selection begins at the beginning of the table or not</li>
 * <li><code>isEndingSelection</code> - check whether the current selection ends at the beginning of the table or not</li>
 * <li><code>isMultilinePropertySelection</code> - check whether the current selection's first element is a {@link PropertyRecord}
 * with multiline enabled.</li>
 * <li><code>isNormalPropertySelection</code> - check whether the current selection's first element is a {@link PropertyRecord}
 * with multiline disabled.</li>
 * </ul>
 * @author skzs
 */
public class MPEPropertyTester extends PropertyTester
{

	/**
	 * Property indicating that the selection is monolithic.
	 */
	private static final String PROPERTY_IS_CONTINUOUS_SELECTION = "isContinuousSelection"; //$NON-NLS-1$

	/**
	 * Property indicating that the selection begins at the beginning of the table.
	 */
	private static final String PROPERTY_IS_BEGINNING_SELECTION = "isBeginningSelection"; //$NON-NLS-1$

	/**
	 * Property indicating that the selection ends at the beginning of the table.
	 */
	private static final String PROPERTY_IS_ENDING_SELECTION = "isEndingSelection"; //$NON-NLS-1$

	/**
	 * Property indicating that the current selection is a {@link PropertyRecord}
	 * with multiline enabled.
	 */
	private static final String PROPERTY_MULTILINE_PROPERTY_SELECTION = "isMultilinePropertySelection"; //$NON-NLS-1$

	/**
	 * Property indicating that the current selection is a {@link PropertyRecord}
	 * with multiline disabled.
	 */
	private static final String PROPERTY_NORMAL_PROPERTY_SELECTION = "isNormalPropertySelection"; //$NON-NLS-1$

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String, java.lang.Object[], java.lang.Object)
	 */
	public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue)
	{
		final Editor editor = getEditor();
		if (editor == null)
			return false;

		if (PROPERTY_IS_CONTINUOUS_SELECTION.equals(property))
		{
			if (receiver instanceof AbstractRecord)
				return true;
			else if (receiver instanceof StructuredSelection)
				return isContinuousSelection(editor.getTable(), (StructuredSelection) receiver);
			return false;
		}
		else if (PROPERTY_IS_BEGINNING_SELECTION.equals(property))
		{
			AbstractRecord record = null;
			if (receiver instanceof AbstractRecord)
			{
				record = (AbstractRecord) receiver;
			}
			else if (receiver instanceof StructuredSelection)
			{
				final StructuredSelection structuredSelection = (StructuredSelection) receiver;
				if (structuredSelection.isEmpty())
					return false;
				if (!(structuredSelection.getFirstElement() instanceof AbstractRecord))
					return false;
				record = (AbstractRecord) structuredSelection.getFirstElement();
			}
			else
				return false;
			return editor.getTable().indexOf(record) == 0;
		}
		else if (PROPERTY_IS_ENDING_SELECTION.equals(property))
		{
			AbstractRecord record = null;
			if (receiver instanceof AbstractRecord)
			{
				record = (AbstractRecord) receiver;
			}
			else if (receiver instanceof StructuredSelection)
			{
				final StructuredSelection structuredSelection = (StructuredSelection) receiver;
				if (structuredSelection.isEmpty())
					return false;

				final Object[] recordObjects = structuredSelection.toArray();
				if (!(recordObjects[recordObjects.length - 1] instanceof AbstractRecord))
					return false;
				record = (AbstractRecord) recordObjects[recordObjects.length - 1];
			}
			else
				return false;
			return editor.getTable().indexOf(record) == editor.getTable().size() - 1;
		}
		else if (PROPERTY_MULTILINE_PROPERTY_SELECTION.equals(property))
		{
			PropertyRecord record = null;
			if (receiver instanceof PropertyRecord)
			{
				record = (PropertyRecord) receiver;
			}
			else if (receiver instanceof StructuredSelection)
			{
				final StructuredSelection structuredSelection = (StructuredSelection) receiver;
				if (structuredSelection.isEmpty())
					return false;

				record = (PropertyRecord) structuredSelection.getFirstElement();
			}
			else
				return false;
			return record.isMultiLine();
		}
		else if (PROPERTY_NORMAL_PROPERTY_SELECTION.equals(property))
		{
			PropertyRecord record = null;
			if (receiver instanceof PropertyRecord)
			{
				record = (PropertyRecord) receiver;
			}
			else if (receiver instanceof StructuredSelection)
			{
				final StructuredSelection structuredSelection = (StructuredSelection) receiver;
				if (structuredSelection.isEmpty())
					return false;

				record = (PropertyRecord) structuredSelection.getFirstElement();
			}
			else
				return false;
			return !record.isMultiLine();
		}
		else
		{
			System.err.println("Unsupported property: " + property); //$NON-NLS-1$
			return false;
		}
	}

	private Editor getEditor()
	{
		final IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (activeWorkbenchWindow == null)
			return null;

		final IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
		if (activePage == null)
			return null;

		final IEditorPart activeEditor = activePage.getActiveEditor();
		if (activeEditor == null)
			return null;

		if (!(activeEditor instanceof Editor))
			return null;

		return (Editor) activeEditor;
	}

	/**
	 * Returns <code>true</code> if the selection is continuous, otherwise it returns <code>false</code>
	 * @param table the given {@link Table} instance where the selection was made
	 * @param structuredSelection the given selection
	 * @return <code>true</code> if the selection is continuous, otherwise it returns <code>false</code>
	 */
	private boolean isContinuousSelection(final Table table, final StructuredSelection structuredSelection)
	{
		@SuppressWarnings("rawtypes")
		final Iterator iterator = structuredSelection.iterator();
		int iMin = -1;
		int iMax = -1;
		while (iterator.hasNext())
		{
			final Object object = iterator.next();
			if (!(object instanceof AbstractRecord))
				return false;

			final AbstractRecord record = (AbstractRecord) object;
			final int index = table.indexOf(record);
			if (iMin == -1 && iMax == -1)
			{
				iMin = index;
				iMax = index;
			}
			else
			{
				if (index < iMin)
					iMin = index;
				if (index > iMax)
					iMax = index;
			}
		}
		if (iMax - iMin + 1 == structuredSelection.size())
			return true;
		return false;
	}
}
