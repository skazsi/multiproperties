package hu.skzs.multiproperties.ui.propertytester;

import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.ui.editor.Editor;

import java.util.Iterator;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.PlatformUI;

/**
 * The <code>MPEPropertyTester</code> property tester provides several condition checking to
 * help identifying which commands can be enabled. The <code>plugin.xml</code> use it.
 * <p>Available properties are the followings.</p>
 * <ul>
 * <li><code>isContinuousSelection</code> - check whether the current selection is continuous of {@link AbstractRecord} or not</li>
 * <li><code>isBeginningSelection</code> - check whether the current selection begins at the beginning of the table or not</li>
 * <li><code>isEndingSelection</code> - check whether the current selection ends at the beginning of the table or not</li>
 * </ul>
 * @author sallai
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

	public boolean test(Object receiver, String property, Object[] args, Object expectedValue)
	{
		if (!(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor() instanceof Editor))
			throw new IllegalStateException("The MPE property tester can be used for active MP editor only"); //$NON-NLS-1$
		Editor editor = (Editor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();

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
				StructuredSelection structuredSelection = (StructuredSelection) receiver;
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
				StructuredSelection structuredSelection = (StructuredSelection) receiver;
				if (structuredSelection.isEmpty())
					return false;

				Object[] recordObjects = structuredSelection.toArray();
				if (!(recordObjects[recordObjects.length - 1] instanceof AbstractRecord))
					return false;
				record = (AbstractRecord) recordObjects[recordObjects.length - 1];
			}
			else
				return false;
			return editor.getTable().indexOf(record) == editor.getTable().size() - 1;
		}
		else
		{
			System.err.println("Unsupported property: " + property); //$NON-NLS-1$
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if the selection is continuous, otherwise it returns <code>false</code>
	 * @param table the given {@link Table} instance where the selection was made
	 * @param structuredSelection the given selection
	 * @return <code>true</code> if the selection is continuous, otherwise it returns <code>false</code>
	 */
	private boolean isContinuousSelection(Table table, StructuredSelection structuredSelection)
	{
		@SuppressWarnings("rawtypes")
		Iterator iterator = structuredSelection.iterator();
		int iMin = -1;
		int iMax = -1;
		while (iterator.hasNext())
		{
			Object object = iterator.next();
			if (!(object instanceof AbstractRecord))
				return false;

			AbstractRecord record = (AbstractRecord) object;
			int index = table.indexOf(record);
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
