package hu.skzs.multiproperties.handler.text.wizard;

import hu.skzs.multiproperties.handler.text.Messages;
import hu.skzs.multiproperties.handler.text.configurator.AbstractConfigurator;
import hu.skzs.multiproperties.handler.text.configurator.WorkspaceConfigurator;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class PatternPage extends WizardPage
{

	private final AbstractConfigurator configurator;

	private Text headerText;
	private Text footerText;
	private Text propertyText;
	private Text commentText;
	private Text emptyText;

	public PatternPage(final WorkspaceConfigurator configurator)
	{
		super("column.configuration.page"); //$NON-NLS-1$
		this.configurator = configurator;
		setTitle(Messages.getString("wizard.pattern.title")); //$NON-NLS-1$
		setDescription(Messages.getString("wizard.pattern.description")); //$NON-NLS-1$
	}

	public void createControl(final Composite parent)
	{
		final Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		final GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.verticalSpacing = 5;
		container.setLayout(layout);

		headerText = createField(container,
				Messages.getString("wizard.pattern.header"), configurator.getHeaderPattern()); //$NON-NLS-1$
		footerText = createField(container,
				Messages.getString("wizard.pattern.footer"), configurator.getFooterPattern()); //$NON-NLS-1$
		propertyText = createField(container,
				Messages.getString("wizard.pattern.property"), configurator.getPropertyPattern()); //$NON-NLS-1$
		commentText = createField(container,
				Messages.getString("wizard.pattern.comment"), configurator.getCommentPattern()); //$NON-NLS-1$
		emptyText = createField(container, Messages.getString("wizard.pattern.empty"), configurator.getEmptyPattern()); //$NON-NLS-1$
		dialogChanged();
	}

	private void dialogChanged()
	{
		if (headerText.getText().contains(AbstractConfigurator.DELIM)
				|| footerText.getText().contains(AbstractConfigurator.DELIM)
				|| propertyText.getText().contains(AbstractConfigurator.DELIM)
				|| commentText.getText().contains(AbstractConfigurator.DELIM)
				|| emptyText.getText().contains(AbstractConfigurator.DELIM))
		{
			setErrorMessage(Messages.getString("wizard.pattern.error.invalid.prefix") + AbstractConfigurator.DELIM + Messages.getString("wizard.pattern.error.invalid.suffix")); //$NON-NLS-1$ //$NON-NLS-2$
			setPageComplete(false);
			return;
		}

		if (headerText.getText().trim().equals("") && footerText.getText().trim().equals("") //$NON-NLS-1$ //$NON-NLS-2$
				&& propertyText.getText().trim().equals("") && commentText.getText().trim().equals("") //$NON-NLS-1$//$NON-NLS-2$
				&& emptyText.getText().trim().equals("")) //$NON-NLS-1$
		{
			setErrorMessage(null);
			setPageComplete(false);
			return;
		}
		setErrorMessage(null);
		setPageComplete(true);
	}

	private Text createField(final Composite parent, final String labelText, final String defaultValue)
	{
		final Label label = new Label(parent, SWT.NULL);
		label.setText(labelText);
		final Text text = new Text(parent, SWT.BORDER | SWT.MULTI);
		if (defaultValue != null)
			text.setText(defaultValue);
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 40;
		text.setLayoutData(gridData);
		text.addModifyListener(new ModifyListener()
		{
			public void modifyText(final ModifyEvent e)
			{
				dialogChanged();
			}
		});

		return text;
	}

	public String getHeaderPattern()
	{
		return headerText.getText();
	}

	public String getFooterPattern()
	{
		return footerText.getText();
	}

	public String getPropertyPattern()
	{
		return propertyText.getText();
	}

	public String getCommentPattern()
	{
		return commentText.getText();
	}

	public String getEmptyPattern()
	{
		return emptyText.getText();
	}
}
