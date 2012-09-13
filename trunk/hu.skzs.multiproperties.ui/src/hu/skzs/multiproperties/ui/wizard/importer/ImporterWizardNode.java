package hu.skzs.multiproperties.ui.wizard.importer;

import hu.skzs.multiproperties.base.api.AbstractImporterWizard;
import hu.skzs.multiproperties.base.registry.element.ImporterRegistryElement;
import hu.skzs.multiproperties.ui.Activator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.swt.graphics.Point;

/**
 * A wizard node represents a "potential" wizard. Wizard nodes
 * are used by wizard selection pages to allow the user to pick
 * from several available nested wizards.
 * @author sallai
 */
public class ImporterWizardNode implements IWizardNode
{

	protected ImporterRegistryElement importerRegistryElement;
	protected AbstractImporterWizard importerWizard;

	/**
	 * Default constructor.
	 * @param importerRegistryElement the given {@link ImporterRegistryElement}
	 */
	public ImporterWizardNode(final ImporterRegistryElement importerRegistryElement)
	{
		super();
		this.importerRegistryElement = importerRegistryElement;
	}

	/**
	 * Returns the kept {@link ImporterRegistryElement}
	 * @return the kept {@link ImporterRegistryElement}
	 */
	public ImporterRegistryElement getImporterRegistryElement()
	{
		return importerRegistryElement;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizardNode#dispose()
	 */
	public void dispose()
	{
		// Do nothing since the wizard wasn't created via reflection.
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizardNode#getExtent()
	 */
	public Point getExtent()
	{
		return new Point(-1, -1);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizardNode#getWizard()
	 */
	public AbstractImporterWizard getWizard()
	{
		if (importerWizard != null)
			return importerWizard;

		try
		{
			importerWizard = importerRegistryElement.getImporterWizard();
		}
		catch (final CoreException e)
		{
			Activator.logError("Unexpected error occured during instantiating wizard", e); //$NON-NLS-1$
		}
		return importerWizard;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizardNode#isContentCreated()
	 */
	public boolean isContentCreated()
	{
		return importerWizard != null;
	}
}
