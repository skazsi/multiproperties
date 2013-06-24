package hu.skzs.multiproperties.rcp.wizard;

import hu.skzs.multiproperties.base.io.FileContentAccessor;
import hu.skzs.multiproperties.ui.wizard.AbstractNewMultiPropertiesPage;
import hu.skzs.multiproperties.ui.wizard.AbstractNewMultiPropertiesWizard;

import java.io.File;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

/**
 * The <code>NewMultiPropertiesWizard</code> creates new MultiProperties files.
 * @author skzs
 *
 */
public class NewMultiPropertiesWizard extends AbstractNewMultiPropertiesWizard
{

	public NewMultiPropertiesWizard()
	{
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected AbstractNewMultiPropertiesPage createNewMultiPropertiesPage()
	{
		return new NewMultiPropertiesPage();
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
					final File file = (File) fileContentAccessor.getFile();

					IFileStore fileStore = EFS.getLocalFileSystem().getStore(file.toURI());
					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

					IDE.openEditorOnFileStore(page, fileStore);
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