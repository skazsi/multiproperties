package hu.skzs.multiproperties.ui.wizard;

import hu.skzs.multiproperties.base.io.FileContentAccessor;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;

/**
 * The <code>NewMultiPropertiesWizard</code> creates new MultiProperties files.
 * @author skzs
 *
 */
public class NewMultiPropertiesWizard extends AbstractNewMultiPropertiesWizard implements INewWizard
{
	private ISelection selection;

	public NewMultiPropertiesWizard()
	{
		super();
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected AbstractNewMultiPropertiesPage createNewMultiPropertiesPage()
	{
		return new NewMultiPropertiesPage(selection);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void openEditor(final IWorkbenchPage page, final FileContentAccessor<?> fileContentAccessor)
	{
		getShell().getDisplay().asyncExec(new Runnable()
		{
			public void run()
			{
				try
				{
					final IFile file = (IFile) fileContentAccessor.getFile();
					IDE.openEditor(page, file, true);
				}
				catch (final PartInitException e)
				{
					// TODO: missing exception handling
					e.printStackTrace();
				}
			}
		});
	}
}