package com.ind.multiproperties.ui;

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
	public static final String PLUGIN_ID = "com.ind.multiproperties.ui";

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
		return AbstractUIPlugin.imageDescriptorFromPlugin("com.ind.multiproperties.ui", path);
	}

	@Override
	protected void initializeImageRegistry(final ImageRegistry reg)
	{
		reg.put("overview", getImageDescriptor("icons/overview.gif"));
		reg.put("table", getImageDescriptor("icons/table.gif"));
		reg.put("columns", getImageDescriptor("icons/columns.gif"));
		reg.put("column", getImageDescriptor("icons/column.gif"));
		reg.put("table", getImageDescriptor("icons/table.gif"));
		reg.put("record", getImageDescriptor("icons/record.gif"));
		reg.put("comment", getImageDescriptor("icons/comment.gif"));
		reg.put("empty", getImageDescriptor("icons/empty.gif"));
		reg.put("warning", getImageDescriptor("icons/warning.gif"));
		reg.put("disabled", getImageDescriptor("icons/disabled.gif"));
		reg.put("insert", getImageDescriptor("icons/insert.gif"));
		reg.put("edit", getImageDescriptor("icons/edit.gif"));
		reg.put("edit_wiz", getImageDescriptor("icons/edit_wiz.gif"));
		reg.put("fill_checked", getImageDescriptor("icons/fill_checked.gif"));
		reg.put("fill_all", getImageDescriptor("icons/fill_all.gif"));
		reg.put("delete", getImageDescriptor("icons/delete.gif"));
		reg.put("move_up_e", getImageDescriptor("icons/move_up_e.gif"));
		reg.put("move_up_d", getImageDescriptor("icons/move_up_d.gif"));
		reg.put("move_down_e", getImageDescriptor("icons/move_down_e.gif"));
		reg.put("move_down_d", getImageDescriptor("icons/move_down_d.gif"));
		reg.put("tooltip", getImageDescriptor("icons/tooltip.gif"));
	}

}
