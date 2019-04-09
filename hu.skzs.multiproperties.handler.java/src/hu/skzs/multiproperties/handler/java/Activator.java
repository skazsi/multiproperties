package hu.skzs.multiproperties.handler.java;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class Activator extends AbstractUIPlugin
{

	public static final String PLUGIN_ID = "hu.skzs.multiproperties.handler.java";

	private static Activator plugin;

	@Override
	public void start(final BundleContext context) throws Exception
	{
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(final BundleContext context) throws Exception
	{
		plugin = null;
		super.stop(context);
	}

	public static Activator getDefault()
	{
		return plugin;
	}

	public static void log(final IStatus status)
	{
		Activator.getDefault().getLog().log(status);
	}

	private static IStatus createStatus(final int severity, final int code, final String message,
			final Throwable exception)
	{
		return new Status(severity, Activator.getDefault().getBundle().getSymbolicName(), code, message, exception);
	}

	private static void log(final int severity, final int code, final String message, final Throwable exception)
	{
		log(createStatus(severity, code, message, exception));
	}

	public static void logInfo(final String message)
	{
		log(IStatus.INFO, IStatus.OK, message, null);
	}

	public static void logError(final Throwable exception)
	{
		logError(exception.getMessage(), exception);
	}

	public static void logError(final String message, final Throwable exception)
	{
		log(IStatus.ERROR, IStatus.OK, message, exception);
	}

	public static void logWarn(final Throwable exception)
	{
		logWarn(exception.getMessage(), exception);
	}

	public static void logWarn(final String message, final Throwable exception)
	{
		log(IStatus.WARNING, IStatus.OK, message, exception);
	}

}
