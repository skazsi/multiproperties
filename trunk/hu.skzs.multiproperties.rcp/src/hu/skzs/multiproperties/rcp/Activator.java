package hu.skzs.multiproperties.rcp;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin
{

	// The plug-in ID
	public static final String PLUGIN_ID = "hu.skzs.multiproperties.rcp"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator()
	{
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception
	{
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception
	{
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault()
	{
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path)
	{
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
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
