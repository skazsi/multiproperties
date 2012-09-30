package hu.skzs.multiproperties.handler.text.wizard;

import hu.skzs.multiproperties.handler.text.Messages;
import hu.skzs.multiproperties.handler.text.configurator.WorkspaceConfigurator;

import org.eclipse.jface.wizard.Wizard;

/**
 * The <code>TargetPropertiesSelectionWizard</code> wizard is able to configure a column's target file.
 * When the user saves the MultiProperties file, the related column's content will be saved in this
 * target file. 
 * @author sallai
 */
public class TextHandlerConfigurationWizard extends Wizard
{
	private final WorkspaceConfigurator configurator;
	private TextFileSelectionPage textFileSelectionPage;
	private PatternPage patternPage;

	public TextHandlerConfigurationWizard(final WorkspaceConfigurator configurator)
	{
		this.configurator = configurator;
	}

	@Override
	public void addPages()
	{
		setWindowTitle(Messages.getString("wizard.configuration.title")); //$NON-NLS-1$
		textFileSelectionPage = new TextFileSelectionPage(configurator);
		addPage(textFileSelectionPage);
		patternPage = new PatternPage(configurator);
		addPage(patternPage);
	}

	@Override
	public boolean performFinish()
	{
		configurator.setContainerName(textFileSelectionPage.getLocation());
		configurator.setFileName(textFileSelectionPage.getFileName());
		configurator.setHeaderPattern(patternPage.getHeaderPattern());
		configurator.setFooterPattern(patternPage.getFooterPattern());
		configurator.setPropertyPattern(patternPage.getPropertyPattern());
		configurator.setCommentPattern(patternPage.getCommentPattern());
		configurator.setEmptyPattern(patternPage.getEmptyPattern());
		return true;
	}
}
