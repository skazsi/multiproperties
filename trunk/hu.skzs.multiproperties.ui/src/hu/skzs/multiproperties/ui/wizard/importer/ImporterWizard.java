package hu.skzs.multiproperties.ui.wizard.importer;

import hu.skzs.multiproperties.base.api.AbstractImporterWizard;
import hu.skzs.multiproperties.base.api.IImporter;
import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.ImporterFacade;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.base.registry.element.ImporterRegistryElement;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.Messages;

import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

/**
 * The <code>ImportWizard</code> wizard is able to import a content from different sources by using importers. 
 * @author sallai
 */
public class ImporterWizard extends Wizard
{

	private final Column column;
	private final Table table;
	private ImporterSelectionPage importerSelectionPage;
	private ImporterMethodsPage importMethodsPage;

	public ImporterWizard(final Column column, final Table table)
	{
		this.column = column;
		this.table = table;
	}

	@Override
	public void addPages()
	{
		setWindowTitle(Messages.getString("wizard.import.title")); //$NON-NLS-1$
		importerSelectionPage = new ImporterSelectionPage();
		addPage(importerSelectionPage);
		importMethodsPage = new ImporterMethodsPage(column);
		addPage(importMethodsPage);
	}

	@Override
	public IWizardPage getNextPage(final IWizardPage page)
	{
		if (page == importMethodsPage)
		{
			final ImporterWizardNode importerWizardNode = (ImporterWizardNode) importerSelectionPage.getSelectedNode();
			final boolean isCreated = importerWizardNode.isContentCreated();

			final AbstractImporterWizard wizard = importerWizardNode.getWizard();
			if (!isCreated)
			{
				// Allow the wizard to create its pages
				wizard.init(this);
				wizard.addPages();
			}
			return wizard.getStartingPage();
		}
		return super.getNextPage(page);
	}

	@Override
	public boolean performFinish()
	{
		try
		{
			final ImporterWizardNode importerWizardNode = (ImporterWizardNode) importerSelectionPage.getSelectedNode();

			// Getting the importer
			final ImporterRegistryElement importerRegistryElement = importerWizardNode.getImporterRegistryElement();
			final IImporter importer = importerRegistryElement.getImporter();

			// Getting the wizard
			final AbstractImporterWizard wizard = importerWizardNode.getWizard();

			// Importing the content
			final List<AbstractRecord> records = importer.getRecords(wizard.getInput(), column);

			final ImporterFacade facade = new ImporterFacade();
			facade.init(table, column);

			facade.performImport(records, importMethodsPage.getSelectedMethod());

		}
		catch (final Exception e)
		{
			Activator.logError("Unexpected error occured during importing", e); //$NON-NLS-1$
		}
		return true;
	}
}
