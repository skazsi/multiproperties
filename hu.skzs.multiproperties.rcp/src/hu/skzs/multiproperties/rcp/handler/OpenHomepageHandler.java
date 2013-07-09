package hu.skzs.multiproperties.rcp.handler;

import java.net.URL;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PlatformUI;

public class OpenHomepageHandler extends AbstractHandler
{

	private static final String HOMEPAGE = "https://code.google.com/a/eclipselabs.org/p/multiproperties/"; //$NON-NLS-1$

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands
	 * .ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		try
		{
			PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(new URL(HOMEPAGE));
		}
		catch (Exception e)
		{
			throw new ExecutionException("Unable to handle open homepage command", e); //$NON-NLS-1$
		}
		return null;
	}
}
