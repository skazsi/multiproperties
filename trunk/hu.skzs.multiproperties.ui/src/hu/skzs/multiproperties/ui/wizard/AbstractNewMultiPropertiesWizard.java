package hu.skzs.multiproperties.ui.wizard;

import hu.skzs.multiproperties.base.io.FileContentAccessor;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.base.model.fileformat.ISchemaConverter;
import hu.skzs.multiproperties.base.model.fileformat.SchemaConverterFactory;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.Messages;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

/**
 * The <code>NewMultiPropertiesWizard</code> creates new MultiProperties files.
 * @author skzs
 */
public abstract class AbstractNewMultiPropertiesWizard extends Wizard
{

	protected AbstractNewMultiPropertiesPage page;

	public AbstractNewMultiPropertiesWizard()
	{
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle(Messages.getString("wizard.new.title")); //$NON-NLS-1$
	}

	/**
	 * Returns the {@link AbstractNewMultiPropertiesPage} page which will be added to this wizard
	 * @return the {@link AbstractNewMultiPropertiesPage} page
	 */
	protected abstract AbstractNewMultiPropertiesPage createNewMultiPropertiesPage();

	@Override
	public final void addPages()
	{
		this.page = createNewMultiPropertiesPage();
		addPage(page);
	}

	/**
	 * Opens a new editor for the newly created file.
	 * @param page the active page
	 */
	protected abstract void openEditor(IWorkbenchPage page, final FileContentAccessor<?> fileContentAccessor);

	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard.
	 */
	@Override
	public boolean performFinish()
	{
		final FileContentAccessor<?> fileContentAccessor = page.getFileContentAccessor();

		final Table table = new Table();
		table.setName(page.getMultiPropertiesName());
		table.setDescription(page.getMultiPropertiesDescription());

		try
		{
			final ISchemaConverter schemaConverter = SchemaConverterFactory.getSchemaConverter(table);
			fileContentAccessor.setContent(schemaConverter.convert(table));
		}
		catch (final Exception e)
		{
			Activator.logError("Unable to create the new MultiProperties file", e); //$NON-NLS-1$
		}

		final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		openEditor(page, fileContentAccessor);

		return true;
	}
}