package hu.skzs.multiproperties.ui.command;

import java.util.ResourceBundle;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.FindReplaceAction;

/**
 * The <code>FindReplaceHandler</code> activates the {@link FindReplaceAction}.
 * @author sallai
 */
public class FindReplaceHandler extends AbstractHandler
{

	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		final ResourceBundle bundle = ResourceBundle.getBundle("org.eclipse.ui.texteditor.ConstructedEditorMessages"); //$NON-NLS-1$
		// TODO: the below "Editor.FindReplace." prefix and bundle probably could be eliminated by custom and own resources  
		Action findReplaceAction = new FindReplaceAction(bundle, "Editor.FindReplace.", HandlerUtil.getActiveEditor(event)); //$NON-NLS-1$
		findReplaceAction.run();
		return null;
	}

}
