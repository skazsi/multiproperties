package hu.skzs.multiproperties.ui.command;

import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.ui.editor.Editor;
import hu.skzs.multiproperties.ui.property.AbstractRecordPreferenceManagerBuilder;
import hu.skzs.multiproperties.ui.property.AbstractRecordPropertyPage;
import hu.skzs.multiproperties.ui.property.PreferenceManagerBuilderFactory;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class EditHandler extends AbstractHandler
{

	/**
	 * The <code>COMMAND_ID</code> represents the <strong>edit</strong> command identifier. It is the same
	 * value than how the command is specified in the <code>plugin.xml</code>.
	 */
	public static final String COMMAND_ID = "hu.skzs.multiproperties.ui.command.Edit"; //$NON-NLS-1$

	private AbstractRecordPropertyPage<AbstractRecord> previouslySelectedPropertyPage;

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
		final AbstractRecord record = (AbstractRecord) structuredSelection.getFirstElement();

		final Shell shell = HandlerUtil.getActiveShell(event);

		editRecord(shell, record, editor.getTable());

		return null;
	}

	private void editRecord(final Shell shell, final AbstractRecord record, final Table table)
	{
		final AbstractRecord tempPropertyRecord = createWorkingCopyRecord(record);
		final PreferenceManagerBuilderFactory factory = new PreferenceManagerBuilderFactory(tempPropertyRecord, record,
				table);
		final AbstractRecordPreferenceManagerBuilder<?> builder = factory.getBuilder();

		final PreferenceDialog dialog = new PreferenceDialog(shell, builder.buildPreferenceManager());
		dialog.addPageChangedListener(new IPageChangedListener()
		{
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void pageChanged(final PageChangedEvent event)
			{
				if (previouslySelectedPropertyPage != null)
					previouslySelectedPropertyPage.storeToModel();

				previouslySelectedPropertyPage = (AbstractRecordPropertyPage) event.getSelectedPage();
				previouslySelectedPropertyPage.updateFromModel();
			}
		});

		dialog.create();
		dialog.getTreeViewer().expandAll();
		if (dialog.open() == Window.OK)
		{
			updateMasterCopyRecord(record, tempPropertyRecord);
		}
		previouslySelectedPropertyPage = null;
	}

	private AbstractRecord createWorkingCopyRecord(final AbstractRecord record)
	{
		if (record instanceof PropertyRecord)
			return new PropertyRecord((PropertyRecord) record);
		else if (record instanceof CommentRecord)
			return new CommentRecord((CommentRecord) record);
		else
			return null;
	}

	private void updateMasterCopyRecord(final AbstractRecord master, final AbstractRecord copy)
	{
		if (master instanceof PropertyRecord)
		{
			final PropertyRecord masterPropertyRecord = (PropertyRecord) master;
			final PropertyRecord copyPropertyRecord = (PropertyRecord) copy;
			masterPropertyRecord.set(copyPropertyRecord);
		}
		else
		{
			final CommentRecord masterCommentRecord = (CommentRecord) master;
			final CommentRecord copyCommentRecord = (CommentRecord) copy;
			masterCommentRecord.set(copyCommentRecord);
		}
	}
}
