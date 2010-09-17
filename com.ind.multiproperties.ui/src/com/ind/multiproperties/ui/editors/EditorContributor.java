package com.ind.multiproperties.ui.editors;

import java.util.Iterator;
import java.util.ResourceBundle;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;
import org.eclipse.ui.texteditor.FindReplaceAction;

import com.ind.multiproperties.base.model.AbstractRecord;
import com.ind.multiproperties.base.model.CommentRecord;
import com.ind.multiproperties.base.model.EmptyRecord;
import com.ind.multiproperties.base.model.PropertyRecord;
import com.ind.multiproperties.ui.Activator;
import com.ind.multiproperties.ui.ContentAssistant;
import com.ind.multiproperties.ui.wizards.EditCommentWizard;
import com.ind.multiproperties.ui.wizards.EditPropertyWizard;
import com.ind.multiproperties.ui.wizards.InsertRecordWizard;

public class EditorContributor extends MultiPageEditorActionBarContributor
{

	private IEditorPart activeEditor;
	private IEditorPart activePage;
	private Action copyAction;
	private Action cutAction;
	private Action pasteAction;
	private Action showOverviewPageAction;
	private Action showColumnsPageAction;
	private Action showTablePageAction;
	private Action insertAction;
	private Action editAction;
	private Action deleteAction;
	private Action duplicateAction;
	private Action disableAction;
	private Action moveUpAction;
	private Action moveDownAction;
	private Action tooltipAction;

	public EditorContributor()
	{
		super();
		createActions();
	}

	public Action getInsertAction()
	{
		return insertAction;
	}

	public Action getEditAction()
	{
		return editAction;
	}

	public Action getDuplicateAction()
	{
		return duplicateAction;
	}

	public Action getDeleteAction()
	{
		return deleteAction;
	}

	public Action getDisableAction()
	{
		return disableAction;
	}

	public Action getMoveUpAction()
	{
		return moveUpAction;
	}

	public Action getMoveDownAction()
	{
		return moveDownAction;
	}

	@Override
	public void setActiveEditor(final IEditorPart part)
	{
		this.activeEditor = part;
		super.setActiveEditor(part);
	}

	@Override
	public void setActivePage(final IEditorPart part)
	{
		if (activePage == part)
			return;
		activePage = part;
		if (activePage instanceof OverviewPage)
		{
			getActionBars().clearGlobalActionHandlers();
			getActionBars().updateActionBars();
			showOverviewPageAction.setChecked(true);
			showColumnsPageAction.setChecked(false);
			showTablePageAction.setChecked(false);
			insertAction.setEnabled(false);
			editAction.setEnabled(false);
			duplicateAction.setEnabled(false);
			deleteAction.setEnabled(false);
			disableAction.setEnabled(false);
			moveUpAction.setEnabled(false);
			moveDownAction.setEnabled(false);
		}
		else if (activePage instanceof ColumnsPage)
		{
			getActionBars().clearGlobalActionHandlers();
			getActionBars().updateActionBars();
			showOverviewPageAction.setChecked(false);
			showColumnsPageAction.setChecked(true);
			showTablePageAction.setChecked(false);
			insertAction.setEnabled(false);
			editAction.setEnabled(false);
			deleteAction.setEnabled(false);
			duplicateAction.setEnabled(false);
			disableAction.setEnabled(false);
			moveUpAction.setEnabled(false);
			moveDownAction.setEnabled(false);
		}
		else if (activePage instanceof TablePage)
		{
			final ResourceBundle bundle = ResourceBundle.getBundle("org.eclipse.ui.texteditor.ConstructedEditorMessages"); //$NON-NLS-1$
			getActionBars().setGlobalActionHandler(ActionFactory.FIND.getId(), new FindReplaceAction(bundle, "Editor.FindReplace.", activeEditor));
			getActionBars().setGlobalActionHandler(ActionFactory.COPY.getId(), copyAction);
			getActionBars().setGlobalActionHandler(ActionFactory.CUT.getId(), cutAction);
			getActionBars().setGlobalActionHandler(ActionFactory.PASTE.getId(), pasteAction);
			getActionBars().updateActionBars();
			showOverviewPageAction.setChecked(false);
			showColumnsPageAction.setChecked(false);
			showTablePageAction.setChecked(true);
			insertAction.setEnabled(true);
			refreshActions();
		}
		else
		{
			getActionBars().clearGlobalActionHandlers();
			getActionBars().updateActionBars();
			showOverviewPageAction.setChecked(false);
			showColumnsPageAction.setChecked(false);
			showTablePageAction.setChecked(false);
			insertAction.setEnabled(false);
			editAction.setEnabled(false);
			deleteAction.setEnabled(false);
			duplicateAction.setEnabled(false);
			disableAction.setEnabled(false);
			moveUpAction.setEnabled(false);
			moveDownAction.setEnabled(false);
		}
	}

	private void createActions()
	{
		copyAction = new Action() {

			@Override
			public void run()
			{
				try
				{
					final Editor editor = (Editor) activeEditor;
					final TablePage tablepage = (TablePage) editor.getActiveEditor();
					final TableViewer tableviewer = tablepage.getTableViewer();
					final StructuredSelection selection = (StructuredSelection) tableviewer.getSelection();
					final Object[] aRecord = selection.toArray();
					editor.getClipboard().clear();
					for (int i = 0; i < aRecord.length; i++)
					{
						final AbstractRecord oldrecord = (AbstractRecord) aRecord[i];
						final AbstractRecord newrecord = (AbstractRecord) oldrecord.clone();
						editor.getClipboard().add(newrecord);
					}
					refreshActions();
				}
				catch (final Throwable e)
				{
					e.printStackTrace();
				}
			}
		};
		copyAction.setText("Copy");
		copyAction.setToolTipText("Copy");
		cutAction = new Action() {

			@Override
			public void run()
			{
				try
				{
					final Editor editor = (Editor) activeEditor;
					final TablePage tablepage = (TablePage) editor.getActiveEditor();
					final TableViewer tableviewer = tablepage.getTableViewer();
					final StructuredSelection selection = (StructuredSelection) tableviewer.getSelection();
					final Object[] aRecord = selection.toArray();
					editor.getClipboard().clear();
					for (int i = 0; i < aRecord.length; i++)
					{
						final AbstractRecord record = (AbstractRecord) aRecord[i];
						editor.getTable().remove(editor.getTable().indexOf(record));
						editor.getClipboard().add(record);
					}
					tableviewer.refresh();
					refreshActions();
				}
				catch (final Throwable e)
				{
					e.printStackTrace();
				}
			}
		};
		cutAction.setText("Cut");
		cutAction.setToolTipText("Cut");
		pasteAction = new Action() {

			@Override
			public void run()
			{
				try
				{
					final Editor editor = (Editor) activeEditor;
					final TablePage tablepage = (TablePage) editor.getActiveEditor();
					final TableViewer tableviewer = tablepage.getTableViewer();
					final StructuredSelection selection = (StructuredSelection) tableviewer.getSelection();
					final AbstractRecord record = (AbstractRecord) (selection.getFirstElement());
					for (int i = 0; i < editor.getClipboard().size(); i++)
					{
						final AbstractRecord oldrecord = editor.getClipboard().get(i);
						final AbstractRecord newrecord = (AbstractRecord) oldrecord.clone();
						editor.getTable().insert(newrecord, editor.getTable().indexOf(record));
					}
					tableviewer.refresh();
					refreshActions();
				}
				catch (final Throwable e)
				{
					e.printStackTrace();
				}
			}
		};
		pasteAction.setText("Paste");
		pasteAction.setToolTipText("Paste");
		showOverviewPageAction = new Action() {

			@Override
			public void run()
			{
				final Editor editor = (Editor) activeEditor;
				editor.setActivePage("overview_page");
			}
		};
		showOverviewPageAction.setText("O&verview page");
		showOverviewPageAction.setToolTipText("Show Overview page");
		showOverviewPageAction.setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor("overview"));
		showOverviewPageAction.setChecked(true);
		showColumnsPageAction = new Action() {

			@Override
			public void run()
			{
				final Editor editor = (Editor) activeEditor;
				editor.setActivePage("columns_page");
			}
		};
		showColumnsPageAction.setText("&Columns page");
		showColumnsPageAction.setToolTipText("Show Columns page");
		showColumnsPageAction.setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor("columns"));
		showTablePageAction = new Action() {

			@Override
			public void run()
			{
				final Editor editor = (Editor) activeEditor;
				editor.setActivePage("table_page");
			}
		};
		showTablePageAction.setText("T&able page");
		showTablePageAction.setToolTipText("Show Table page");
		showTablePageAction.setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor("table"));
		insertAction = new Action() {

			@Override
			public void run()
			{
				final Editor editor = ContentAssistant.getEditor();
				final TablePage tablepage = (TablePage) editor.getActiveEditor();
				final TableViewer tableviewer = tablepage.getTableViewer();

				InsertRecordWizard wizard = null;
				final ISelection selection = tablepage.getTableViewer().getSelection();
				if (selection.isEmpty())
				{
					wizard = new InsertRecordWizard(-1);
				}
				else
				{
					final AbstractRecord record = (AbstractRecord) ((IStructuredSelection) selection).getFirstElement();
					wizard = new InsertRecordWizard(editor.getTable().indexOf(record));
				}
				final WizardDialog wizarddialog = new WizardDialog(tableviewer.getTable().getShell(), wizard);
				wizarddialog.open();
			}
		};
		insertAction.setText("Insert...");
		insertAction.setToolTipText("Insert");
		insertAction.setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor("insert"));
		insertAction.setAccelerator(SWT.INSERT);
		editAction = new Action() {

			@Override
			public void run()
			{
				final Editor editor = (Editor) activeEditor;
				final TablePage tablepage = (TablePage) editor.getActiveEditor();
				final TableViewer tableviewer = tablepage.getTableViewer();
				final StructuredSelection selection = (StructuredSelection) tableviewer.getSelection();
				if (selection.isEmpty() || selection.size() > 1)
					return;
				final AbstractRecord record = (AbstractRecord) ((IStructuredSelection) selection).getFirstElement();
				if (record instanceof PropertyRecord)
				{
					final PropertyRecord propertyRecord = (PropertyRecord) record;
					final EditPropertyWizard wizard = new EditPropertyWizard(propertyRecord);
					final WizardDialog wizarddialog = new WizardDialog(tableviewer.getTable().getShell(), wizard);
					wizarddialog.open();
				}
				if (record instanceof CommentRecord)
				{
					final CommentRecord commentRecord = (CommentRecord) record;
					final EditCommentWizard wizard = new EditCommentWizard(commentRecord);
					final WizardDialog wizarddialog = new WizardDialog(tableviewer.getTable().getShell(), wizard);
					wizarddialog.open();
				}
			}
		};
		editAction.setText("Edit...");
		editAction.setToolTipText("Edit");
		editAction.setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor("edit"));
		editAction.setAccelerator(SWT.CTRL | 'R');
		deleteAction = new Action() {

			@Override
			public void run()
			{
				final Editor editor = (Editor) activeEditor;
				final TablePage tablepage = (TablePage) editor.getActiveEditor();
				final TableViewer tableviewer = tablepage.getTableViewer();
				final ISelection selection = tableviewer.getSelection();
				if (selection.isEmpty())
					return;
				if (!MessageDialog.openConfirm(null, "Delete", "Are you sure to delete selected records?"))
					return;
				final IStructuredSelection structuredselection = (IStructuredSelection) selection;
				final Iterator itSelection = structuredselection.iterator();
				while (itSelection.hasNext())
				{
					final AbstractRecord record = (AbstractRecord) itSelection.next();
					final int index = editor.getTable().indexOf(record);
					editor.getTable().remove(index);
				}
				tableviewer.refresh(false);
				refreshActions();
			}
		};
		deleteAction.setText("Delete");
		deleteAction.setToolTipText("Delete");
		deleteAction.setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor("delete"));
		deleteAction.setAccelerator(SWT.CTRL | 'D');
		duplicateAction = new Action() {

			@Override
			public void run()
			{
				try
				{
					final Editor editor = (Editor) activeEditor;
					final TablePage tablepage = (TablePage) editor.getActiveEditor();
					final TableViewer tableviewer = tablepage.getTableViewer();
					final StructuredSelection selection = (StructuredSelection) tableviewer.getSelection();
					final Object[] aRecord = selection.toArray();
					for (int i = 0; i < aRecord.length; i++)
					{
						final AbstractRecord oldrecord = (AbstractRecord) aRecord[i];
						final AbstractRecord newrecord = (AbstractRecord) oldrecord.clone();
						final int index = editor.getTable().indexOf(oldrecord) + selection.size();
						editor.getTable().insert(newrecord, index);
					}
					tableviewer.refresh(false);
					refreshActions();
				}
				catch (final Throwable e)
				{
					e.printStackTrace();
				}
			}
		};
		duplicateAction.setText("Duplicate");
		duplicateAction.setToolTipText("Duplicate");
		duplicateAction.setAccelerator(SWT.CTRL | 'J');
		disableAction = new Action("Disabled property", Action.AS_CHECK_BOX) {

			@Override
			public void run()
			{
				final Editor editor = (Editor) activeEditor;
				final TablePage tablepage = (TablePage) editor.getActiveEditor();
				final TableViewer tableviewer = tablepage.getTableViewer();
				final ISelection selection = tableviewer.getSelection();
				if (selection.isEmpty())
					return;
				final PropertyRecord propertyrecord = (PropertyRecord) ((IStructuredSelection) selection).getFirstElement();
				propertyrecord.setDisabled(!propertyrecord.isDisabled());
				tablepage.getTableViewer().update(propertyrecord, null);
				if (editor.getOutlinePage() != null)
					editor.getOutlinePage().update(propertyrecord);
				disableAction.setChecked(propertyrecord.isDisabled());
			}
		};
		disableAction.setToolTipText("Disabled property");
		moveUpAction = new Action() {

			@Override
			public void run()
			{
				final Editor editor = (Editor) activeEditor;
				final TablePage tablepage = (TablePage) editor.getActiveEditor();
				final TableViewer tableviewer = tablepage.getTableViewer();
				final StructuredSelection selection = (StructuredSelection) tableviewer.getSelection();
				final Object[] aRecord = selection.toArray();
				for (int i = 0; i < aRecord.length; i++)
				{
					final AbstractRecord record = (AbstractRecord) aRecord[i];
					editor.getTable().moveUp(record);
				}
				tableviewer.refresh(false);
				refreshActions();
			}
		};
		moveUpAction.setText("Move up");
		moveUpAction.setToolTipText("Move up");
		moveUpAction.setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor("move_up_e"));
		moveUpAction.setDisabledImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor("move_up_d"));
		moveUpAction.setAccelerator(SWT.CTRL | SWT.ARROW_UP);
		moveDownAction = new Action() {

			@Override
			public void run()
			{
				final Editor editor = (Editor) activeEditor;
				final TablePage tablepage = (TablePage) editor.getActiveEditor();
				final TableViewer tableviewer = tablepage.getTableViewer();
				final StructuredSelection selection = (StructuredSelection) tableviewer.getSelection();
				final Object[] aRecord = selection.toArray();
				for (int i = aRecord.length - 1; i >= 0; i--)
				{
					final AbstractRecord record = (AbstractRecord) aRecord[i];
					editor.getTable().moveDown(record);
				}
				tableviewer.refresh(false);
				refreshActions();
			}
		};
		moveDownAction.setText("Move down");
		moveDownAction.setToolTipText("Move down");
		moveDownAction.setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor("move_down_e"));
		moveDownAction.setDisabledImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor("move_down_d"));
		moveDownAction.setAccelerator(SWT.CTRL | SWT.ARROW_DOWN);
		tooltipAction = new Action() {

			@Override
			public void run()
			{
				Activator.getDefault().getPreferenceStore().setValue("tooltip", tooltipAction.isChecked());
			}
		};
		tooltipAction.setText("Show tooltip");
		tooltipAction.setToolTipText("Show tooltip");
		tooltipAction.setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor("tooltip"));
		tooltipAction.setChecked(Activator.getDefault().getPreferenceStore().getBoolean("tooltip"));
	}

	@Override
	public void contributeToMenu(final IMenuManager manager)
	{
		final IMenuManager menu = new MenuManager("&MultiProperties");
		manager.prependToGroup(IWorkbenchActionConstants.MB_ADDITIONS, menu);
		menu.add(showOverviewPageAction);
		menu.add(showColumnsPageAction);
		menu.add(showTablePageAction);
		menu.add(new Separator());
		menu.add(insertAction);
		menu.add(editAction);
		menu.add(deleteAction);
		menu.add(new Separator());
		menu.add(duplicateAction);
		menu.add(disableAction);
		menu.add(new Separator());
		menu.add(moveUpAction);
		menu.add(moveDownAction);
	}

	@Override
	public void contributeToToolBar(final IToolBarManager manager)
	{
		manager.add(new Separator());
		manager.add(moveUpAction);
		manager.add(moveDownAction);
		manager.add(new Separator());
		manager.add(tooltipAction);
	}

	public void refreshActions()
	{
		final Editor editor = (Editor) activeEditor;
		final TablePage tablepage = (TablePage) activePage;
		final StructuredSelection selection = (StructuredSelection) tablepage.getTableViewer().getSelection();
		if (selection.isEmpty())
		{
			copyAction.setEnabled(false);
			cutAction.setEnabled(false);
			pasteAction.setEnabled(false);
			editAction.setEnabled(false);
			deleteAction.setEnabled(false);
			duplicateAction.setEnabled(false);
			disableAction.setEnabled(false);
			moveUpAction.setEnabled(false);
			moveDownAction.setEnabled(false);
			return;
		}
		final boolean bMultiSelect = selection.size() > 1;
		final boolean bContinuous = SelectionProvider.isContinuousSelection(editor.getTable(), selection);
		if (bMultiSelect)
		{
			copyAction.setEnabled(true);
			cutAction.setEnabled(true);
			pasteAction.setEnabled(false);
			editAction.setEnabled(false);
			deleteAction.setEnabled(true);
			duplicateAction.setEnabled(bContinuous);
			disableAction.setEnabled(false);
			disableAction.setChecked(false);
			if (bContinuous)
			{
				moveUpAction.setEnabled(SelectionProvider.isMoveUpEnabled(editor.getTable(), selection));
				moveDownAction.setEnabled(SelectionProvider.isMoveDownEnabled(editor.getTable(), selection));
			}
			else
			{
				moveUpAction.setEnabled(false);
				moveDownAction.setEnabled(false);
			}
		}
		else
		{
			copyAction.setEnabled(true);
			cutAction.setEnabled(true);
			pasteAction.setEnabled(editor.getClipboard().size() > 0);
			final AbstractRecord record = (AbstractRecord) selection.getFirstElement();
			editAction.setEnabled(!(record instanceof EmptyRecord));
			deleteAction.setEnabled(true);
			duplicateAction.setEnabled(true);
			if (record instanceof PropertyRecord)
			{
				final PropertyRecord propertyrecord = (PropertyRecord) record;
				disableAction.setEnabled(true);
				disableAction.setChecked(propertyrecord.isDisabled());
			}
			else
			{
				disableAction.setEnabled(false);
				disableAction.setChecked(false);
			}
			moveUpAction.setEnabled(SelectionProvider.isMoveUpEnabled(editor.getTable(), selection));
			moveDownAction.setEnabled(SelectionProvider.isMoveDownEnabled(editor.getTable(), selection));
		}
	}
}
