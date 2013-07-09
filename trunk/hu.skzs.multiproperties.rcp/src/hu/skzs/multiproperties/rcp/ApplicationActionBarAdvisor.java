package hu.skzs.multiproperties.rcp;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of the
 * actions added to a workbench window. Each window will be populated with
 * new actions.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor
{

	// Actions - important to allocate these only in makeActions, and then use them
	// in the fill methods.  This ensures that the actions aren't recreated
	// when fillActionBars is called with FILL_PROXY.
	private IWorkbenchAction closeAction;
	private IWorkbenchAction closeAllAction;
	private IWorkbenchAction saveAction;
	private IWorkbenchAction saveAsAction;
	private IWorkbenchAction saveAllAction;
	private IWorkbenchAction exitAction;
	private IWorkbenchAction deleteAction;
	private IWorkbenchAction selectAllAction;
	private IWorkbenchAction findAction;
	private IWorkbenchAction newWindowAction;
	private IWorkbenchAction preferencesWindowAction;
	private IWorkbenchAction resetPerspectiveWindowAction;
	private IWorkbenchAction aboutAction;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer)
	{
		super(configurer);
	}

	@Override
	protected void makeActions(final IWorkbenchWindow window)
	{
		// Creates the actions and registers them.
		// Registering is needed to ensure that key bindings work.
		// The corresponding commands keybindings are defined in the plugin.xml file.
		// Registering also provides automatic disposal of the actions when
		// the window is closed.

		closeAction = ActionFactory.CLOSE.create(window);
		register(closeAction);

		closeAllAction = ActionFactory.CLOSE_ALL.create(window);
		register(closeAllAction);

		saveAction = ActionFactory.SAVE.create(window);
		register(saveAction);

		saveAsAction = ActionFactory.SAVE_AS.create(window);
		register(saveAsAction);

		saveAllAction = ActionFactory.SAVE_ALL.create(window);
		register(saveAllAction);

		exitAction = ActionFactory.QUIT.create(window);
		register(exitAction);

		deleteAction = ActionFactory.DELETE.create(window);
		register(deleteAction);

		selectAllAction = ActionFactory.SELECT_ALL.create(window);
		register(selectAllAction);

		findAction = ActionFactory.FIND.create(window);
		register(findAction);

		newWindowAction = ActionFactory.OPEN_NEW_WINDOW.create(window);
		register(newWindowAction);

		preferencesWindowAction = ActionFactory.PREFERENCES.create(window);
		register(preferencesWindowAction);

		resetPerspectiveWindowAction = ActionFactory.RESET_PERSPECTIVE.create(window);
		register(resetPerspectiveWindowAction);

		aboutAction = ActionFactory.ABOUT.create(window);
		register(aboutAction);
	}

	@Override
	protected void fillMenuBar(IMenuManager menuBar)
	{
		MenuManager fileMenu = new MenuManager(Messages.getString("menu.file"), IWorkbenchActionConstants.M_FILE); //$NON-NLS-1$
		MenuManager editMenu = new MenuManager(Messages.getString("menu.edit"), IWorkbenchActionConstants.M_EDIT); //$NON-NLS-1$
		MenuManager windowMenu = new MenuManager(Messages.getString("menu.window"), IWorkbenchActionConstants.M_WINDOW); //$NON-NLS-1$
		MenuManager helpMenu = new MenuManager(Messages.getString("menu.help"), IWorkbenchActionConstants.M_HELP); //$NON-NLS-1$

		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		// Add a group marker indicating where action set menus will appear.
		menuBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
		menuBar.add(windowMenu);
		menuBar.add(helpMenu);

		// File
		fileMenu.add(new GroupMarker("new")); //$NON-NLS-1$
		fileMenu.add(new Separator());
		fileMenu.add(closeAction);
		fileMenu.add(closeAllAction);
		fileMenu.add(new Separator());
		fileMenu.add(saveAction);
		fileMenu.add(saveAsAction);
		fileMenu.add(saveAllAction);
		fileMenu.add(new Separator());
		fileMenu.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
		fileMenu.add(new Separator());
		fileMenu.add(exitAction);

		// Edit
		editMenu.add(deleteAction);
		editMenu.add(selectAllAction);
		editMenu.add(new Separator());
		editMenu.add(findAction);
		editMenu.add(new Separator());
		fileMenu.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));

		// Window
		windowMenu.add(newWindowAction);
		windowMenu.add(new Separator());
		windowMenu.add(resetPerspectiveWindowAction);
		windowMenu.add(new Separator());
		windowMenu.add(preferencesWindowAction);

		// Help
		helpMenu.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
		helpMenu.add(new Separator());
		helpMenu.add(aboutAction);
	}

	@Override
	protected void fillCoolBar(ICoolBarManager coolBar)
	{
		coolBar.add(new GroupMarker(IWorkbenchActionConstants.GROUP_FILE));
		coolBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
	}
}
