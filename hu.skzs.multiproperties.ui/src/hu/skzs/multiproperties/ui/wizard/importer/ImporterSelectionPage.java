package hu.skzs.multiproperties.ui.wizard.importer;

import hu.skzs.multiproperties.base.registry.element.ImporterRegistryElement;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.Messages;
import hu.skzs.multiproperties.ui.wizard.AbstractWizardSelectionPage;
import hu.skzs.multiproperties.ui.wizard.RegistryElementContentProvider;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.wizard.IWizardNode;

/**
 * The {@link ImporterSelectionPage} wizard page lists all of the available importers and the users
 * is able to choose one of them.
 * @author sallai
 */
public class ImporterSelectionPage extends AbstractWizardSelectionPage
{

	/**
	 * Default constructor.
	 */
	public ImporterSelectionPage()
	{
		super("importers.page", Messages.getString("wizard.import.importers.label")); //$NON-NLS-1$ //$NON-NLS-2$
		setTitle(Messages.getString("wizard.import.importers.title")); //$NON-NLS-1$
		setDescription(Messages.getString("wizard.import.importers.description")); //$NON-NLS-1$

		if (Activator.getDefault().getImporterRegistry().getElements().size() == 0)
		{
			setMessage(Messages.getString("wizard.import.importers.warning"), WARNING); //$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.ui.wizard.AbstractSelectionPage#createWizardNode(java.lang.Object)
	 */
	@Override
	protected IWizardNode createWizardNode(final Object element)
	{
		final ImporterRegistryElement importerRegistryElement = (ImporterRegistryElement) element;
		return new ImporterWizardNode(importerRegistryElement);
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.ui.wizard.AbstractSelectionPage#getContentProvider()
	 */
	@Override
	public IStructuredContentProvider getContentProvider()
	{
		return new RegistryElementContentProvider<ImporterRegistryElement>();
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.ui.wizard.AbstractSelectionPage#getLabelProvider()
	 */
	@Override
	public ColumnLabelProvider getLabelProvider()
	{
		return new ImporterSelectionLabelProvider();
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.ui.wizard.AbstractSelectionPage#getInput()
	 */
	@Override
	public Object getInput()
	{
		return Activator.getDefault().getImporterRegistry().getElements();
	}
}
