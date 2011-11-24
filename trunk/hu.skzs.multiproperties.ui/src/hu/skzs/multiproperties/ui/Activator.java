package hu.skzs.multiproperties.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin
{

	// The plug-in ID
	public static final String PLUGIN_ID = "hu.skzs.multiproperties.ui"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	private static FormToolkit toolkit = new FormToolkit(Display.getCurrent());

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
	public void start(final BundleContext context) throws Exception
	{
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext context) throws Exception
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

	public static FormToolkit getToolkit()
	{
		return toolkit;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(final String path)
	{
		return AbstractUIPlugin.imageDescriptorFromPlugin("hu.skzs.multiproperties.ui", path); //$NON-NLS-1$
	}

	@Override
	protected void initializeImageRegistry(final ImageRegistry reg)
	{
		reg.put("overview", getImageDescriptor("icons/overview.gif")); //$NON-NLS-1$//$NON-NLS-2$
		reg.put("table", getImageDescriptor("icons/table.gif")); //$NON-NLS-1$//$NON-NLS-2$
		reg.put("columns", getImageDescriptor("icons/columns.gif")); //$NON-NLS-1$//$NON-NLS-2$
		reg.put("column", getImageDescriptor("icons/column.gif")); //$NON-NLS-1$//$NON-NLS-2$
		reg.put("table", getImageDescriptor("icons/table.gif")); //$NON-NLS-1$//$NON-NLS-2$
		reg.put("record", getImageDescriptor("icons/record.gif")); //$NON-NLS-1$//$NON-NLS-2$
		reg.put("comment", getImageDescriptor("icons/comment.gif")); //$NON-NLS-1$//$NON-NLS-2$
		reg.put("empty", getImageDescriptor("icons/empty.gif")); //$NON-NLS-1$//$NON-NLS-2$
		reg.put("warning", getImageDescriptor("icons/warning.gif")); //$NON-NLS-1$//$NON-NLS-2$
		reg.put("disabled", getImageDescriptor("icons/disabled.gif")); //$NON-NLS-1$//$NON-NLS-2$
		reg.put("insert", getImageDescriptor("icons/insert.gif")); //$NON-NLS-1$//$NON-NLS-2$
		reg.put("edit", getImageDescriptor("icons/edit.gif")); //$NON-NLS-1$//$NON-NLS-2$
		reg.put("edit_wiz", getImageDescriptor("icons/edit_wiz.gif")); //$NON-NLS-1$//$NON-NLS-2$
		reg.put("fill_checked", getImageDescriptor("icons/fill_checked.gif")); //$NON-NLS-1$//$NON-NLS-2$
		reg.put("fill_all", getImageDescriptor("icons/fill_all.gif")); //$NON-NLS-1$//$NON-NLS-2$
		reg.put("delete", getImageDescriptor("icons/delete.gif")); //$NON-NLS-1$//$NON-NLS-2$
		reg.put("move_up_e", getImageDescriptor("icons/move_up_e.gif")); //$NON-NLS-1$//$NON-NLS-2$
		reg.put("move_up_d", getImageDescriptor("icons/move_up_d.gif")); //$NON-NLS-1$//$NON-NLS-2$
		reg.put("move_down_e", getImageDescriptor("icons/move_down_e.gif")); //$NON-NLS-1$//$NON-NLS-2$
		reg.put("move_down_d", getImageDescriptor("icons/move_down_d.gif")); //$NON-NLS-1$//$NON-NLS-2$
		reg.put("tooltip", getImageDescriptor("icons/tooltip.gif")); //$NON-NLS-1$//$NON-NLS-2$
	}

}
