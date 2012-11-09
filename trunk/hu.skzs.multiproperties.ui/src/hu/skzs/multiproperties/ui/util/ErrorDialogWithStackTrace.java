package hu.skzs.multiproperties.ui.util;

import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.Messages;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * The {@link ErrorDialogWithStackTrace} is a helper class for representing error messages with stack trace details.
 * @author skzs
 *
 */
public class ErrorDialogWithStackTrace
{
	/**
	 * Shows JFace ErrorDialog but improved by constructing full stack trace in
	 * detail area.
	 */
	public static void openError(final Shell parent, final String message, final Throwable t)
	{
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);

		final String trace = sw.toString(); // stack trace as a string

		// Temp holder of child statuses
		final List<Status> childStatuses = new ArrayList<Status>();

		// Split output by OS-independend new-line
		for (final String line : trace.split(System.getProperty("line.separator"))) //$NON-NLS-1$
		{
			// build & add status
			childStatuses.add(new Status(IStatus.ERROR, Activator.PLUGIN_ID, line));
		}

		final MultiStatus ms = new MultiStatus(Activator.PLUGIN_ID, IStatus.ERROR,
				childStatuses.toArray(new Status[] {}), // convert to array of statuses
				t.getLocalizedMessage(), t);

		ErrorDialog.openError(parent, Messages.getString("general.error.title"), message, ms); //$NON-NLS-1$
	}
}
