package hu.skzs.multiproperties.ui.wizard;

import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.base.model.fileformat.ISchemaConverter;
import hu.skzs.multiproperties.base.model.fileformat.SchemaConverterException;
import hu.skzs.multiproperties.base.model.fileformat.SchemaConverterFactory;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.Messages;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

/**
 * The <code>NewMultiPropertiesWizard</code> creates new MultiProperties files.
 * @author Krisztian_Zsolt_Sall
 *
 */
public class NewMultiPropertiesWizard extends Wizard implements INewWizard
{
	private NewMultiPropertiesWizardPage page;
	private ISelection selection;

	public NewMultiPropertiesWizard()
	{
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle(Messages.getString("wizard.new.title")); //$NON-NLS-1$
	}

	@Override
	public void addPages()
	{
		page = new NewMultiPropertiesWizardPage(selection);
		addPage(page);
	}

	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	@Override
	public boolean performFinish()
	{
		final String containerName = page.getLocation();
		final String fileName = page.getFileName();

		final Table table = new Table();
		table.setName(page.getName());

		final IRunnableWithProgress op = new IRunnableWithProgress()
		{
			public void run(final IProgressMonitor monitor) throws InvocationTargetException
			{
				try
				{
					doFinish(table, containerName, fileName, monitor);
				}
				catch (final CoreException e)
				{
					throw new InvocationTargetException(e);
				}
				finally
				{
					monitor.done();
				}
			}
		};
		try
		{
			getContainer().run(true, false, op);
		}
		catch (final InterruptedException e)
		{
			return false;
		}
		catch (final InvocationTargetException e)
		{
			final Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), Messages.getString("general.error.title"), realException.getMessage()); //$NON-NLS-1$
			return false;
		}
		return true;
	}

	/**
	 * Creates a new MultiProperties file based on the given {@link Table}. It will be created in the <code>containerName</code> with <code>fileName</code>.
	 * When the file is created it will be also opened.
	 * @param containerName the container name
	 * @param fileName the desired file name
	 * @param monitor the progress monitor
	 * @throws CoreException
	 */
	private void doFinish(final Table table, final String containerName, final String fileName,
			final IProgressMonitor monitor) throws CoreException
	{
		// create a sample file
		monitor.beginTask(Messages.getString("wizard.new.progress.title"), 2); //$NON-NLS-1$
		monitor.setTaskName(Messages.getString("wizard.new.progress.creating")); //$NON-NLS-1$
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		final IResource resource = root.findMember(new Path(containerName));
		if (!resource.exists() || !(resource instanceof IContainer))
		{
			throwCoreException("Container \"" + containerName + "\" does not exist."); //$NON-NLS-1$ //$NON-NLS-2$
		}
		final IContainer container = (IContainer) resource;
		final IFile file = container.getFile(new Path(fileName));

		try
		{
			final ISchemaConverter schemaConverter = SchemaConverterFactory.getSchemaConverter();
			schemaConverter.convert(file, table);
		}
		catch (final SchemaConverterException e)
		{
			Activator.logError("Unable to create the new MultiProperties file", e); //$NON-NLS-1$
			throwCoreException("Unable to create the new MultiProperties file"); //$NON-NLS-1$
		}
		monitor.worked(1);
		monitor.setTaskName(Messages.getString("wizard.new.progress.opening")); //$NON-NLS-1$
		getShell().getDisplay().asyncExec(new Runnable()
		{
			public void run()
			{
				final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try
				{
					IDE.openEditor(page, file, true);
				}
				catch (final PartInitException e)
				{
				}
			}
		});
		monitor.worked(1);
	}

	private void throwCoreException(final String message) throws CoreException
	{
		final IStatus status = new Status(IStatus.ERROR, "hu.skzs.multiproperties.ui", IStatus.OK, message, null); //$NON-NLS-1$
		throw new CoreException(status);
	}

	/**
	 * We will accept the selection in the workbench to see if
	 * we can initialize from it.
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(final IWorkbench workbench, final IStructuredSelection selection)
	{
		this.selection = selection;
	}
}