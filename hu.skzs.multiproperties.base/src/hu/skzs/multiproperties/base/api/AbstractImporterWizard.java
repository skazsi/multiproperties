package hu.skzs.multiproperties.base.api;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.Wizard;

/**
 * Abstract class for creating importer wizards.
 * @author sallai
 */
public abstract class AbstractImporterWizard extends Wizard
{

	private IWizard parentWizard;

	/**
	 * Initializes the {@link AbstractImporterWizard}
	 * <p>Client should not call this method.</p>
	 * @param importerWizard the given {@link AbstractImporterWizard}
	 */
	public void init(final IWizard parentWizard)
	{
		this.parentWizard = parentWizard;
	}

	/**
	 * Returns the input object based on the user selection on the given wizard.
	 * <p>This input object will be passed to the selected {@link IImporter} implementation,
	 * when the importing framework invokes the {@link IImporter#getRecords(Object, hu.skzs.multiproperties.base.model.Column)}
	 * method on it. The {@link IImporter} implementation must familiar with it.</p>
	 * @return the input object based on the user selection on the given wizard
	 */
	public abstract Object getInput();

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public final boolean performFinish()
	{
		//return parentWizard.performFinish();
		// TODO: why the above line is needed?
		return true;
	}
}
