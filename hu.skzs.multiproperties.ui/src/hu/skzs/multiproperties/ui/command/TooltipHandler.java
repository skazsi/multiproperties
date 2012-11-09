package hu.skzs.multiproperties.ui.command;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * The <code>TooltipHandler</code> toggles the tooltip.
 * @author skzs
 */
public class TooltipHandler extends AbstractHandler
{

	/**
	 * The <code>COMMAND_ID</code> represents the command identifier. It is the same
	 * value than how the related command is specified in the <code>plugin.xml</code>.
	 */
	public static final String COMMAND_ID = "hu.skzs.multiproperties.ui.command.ToggleTooltip"; //$NON-NLS-1$

	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		HandlerUtil.toggleCommandState(event.getCommand());
		return null;
	}

}
