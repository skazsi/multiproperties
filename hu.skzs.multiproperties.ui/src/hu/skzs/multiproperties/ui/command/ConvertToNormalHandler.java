package hu.skzs.multiproperties.ui.command;

import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.ui.Messages;
import hu.skzs.multiproperties.ui.editor.Editor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class ConvertToNormalHandler extends AbstractHandler
{

	/**
	 * The <code>COMMAND_ID</code> represents the <strong>convert to normal</strong> command identifier. It is the same
	 * value than how the command is specified in the <code>plugin.xml</code>.
	 */
	public static final String COMMAND_ID = "hu.skzs.multiproperties.ui.command.ConvertToNormal"; //$NON-NLS-1$

	public Object execute(final ExecutionEvent event) throws ExecutionException
	{
		// Editor instance
		final IEditorPart editorPart = HandlerUtil.getActiveEditor(event);
		if (!(editorPart instanceof Editor))
			return null;
		final Editor editor = (Editor) editorPart;

		// Selected record
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection == null || selection.isEmpty())
			return null;
		if (!(selection instanceof IStructuredSelection))
			return null;
		final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		final PropertyRecord record = (PropertyRecord) structuredSelection.getFirstElement();

		final Shell shell = HandlerUtil.getActiveShell(event);

		if (MessageDialog.openConfirm(shell,
				Messages.getString("general.confirm.title"), Messages.getString("command.convert.normal.confirm"))) //$NON-NLS-1$ //$NON-NLS-2$
		{
			final Table table = editor.getTable();
			for (final Column column : table.getColumns().toArray())
			{
				if (record.getColumnValue(column) != null)
				{
					record.putColumnValue(column, removedLineBreak(record.getColumnValue(column)));
				}
			}
			if (record.getDefaultColumnValue() != null)
			{
				record.setDefaultColumnValue(removedLineBreak(record.getDefaultColumnValue()));
			}
			record.setMultiLine(!record.isMultiLine());
		}
		return null;
	}

	private String removedLineBreak(final String value) throws ExecutionException
	{
		final BufferedReader reader = new BufferedReader(new StringReader(value));
		try
		{
			final StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null)
			{
				stringBuilder.append(line);
			}
			if (reader != null)
				reader.close();
			return stringBuilder.toString();
		}
		catch (final IOException e)
		{
			throw new ExecutionException("Unexpected error occurred", e); //$NON-NLS-1$
		}
	}
}
