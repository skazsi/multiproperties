package hu.skzs.multiproperties.ui.command;

import hu.skzs.multiproperties.base.model.PropertyRecord;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class ConvertToMultilineHandler extends AbstractHandler
{

	/**
	 * The <code>COMMAND_ID</code> represents the <strong>convert to multiline</strong> command identifier. It is the same
	 * value than how the command is specified in the <code>plugin.xml</code>.
	 */
	public static final String COMMAND_ID = "hu.skzs.multiproperties.ui.command.ConvertToMultiline"; //$NON-NLS-1$

	public Object execute(final ExecutionEvent event) throws ExecutionException
	{
		// Selected record
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection == null || selection.isEmpty())
			return null;
		if (!(selection instanceof IStructuredSelection))
			return null;
		final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		final PropertyRecord record = (PropertyRecord) structuredSelection.getFirstElement();

		record.setMultiLine(!record.isMultiLine());

		return null;
	}
}