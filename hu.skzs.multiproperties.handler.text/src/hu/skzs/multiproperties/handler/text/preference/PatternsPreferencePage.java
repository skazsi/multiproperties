package hu.skzs.multiproperties.handler.text.preference;

import hu.skzs.multiproperties.handler.text.Messages;
import hu.skzs.multiproperties.handler.text.configurator.AbstractConfigurator;
import hu.skzs.multiproperties.handler.text.configurator.WorkspaceConfigurator;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * The {@link PatternsPreferencePage} offers specifying the target output file.
 * @author sallai
 */
public class PatternsPreferencePage extends PreferencePage
{

	private final AbstractConfigurator configurator;
	private boolean isCreatedPage;
	private Text headerText;
	private Text footerText;
	private Text propertyText;
	private Text commentText;
	private Text emptyText;

	/**
	 * Default constructor. Sets the preference store for the {@link FieldEditorPreferencePage} and
	 * set the description of the preference page.
	 * @param configurator the given configurator
	 */
	public PatternsPreferencePage(final WorkspaceConfigurator configurator)
	{
		super();
		noDefaultAndApplyButton();
		this.configurator = configurator;
		setTitle(Messages.getString("preference.pattern.title")); //$NON-NLS-1$
		setDescription(Messages.getString("preference.pattern.description")); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(final Composite parent)
	{
		final Composite composite = createComposite(parent);

		headerText = createField(composite,
				Messages.getString("preference.pattern.header"), configurator.getHeaderPattern()); //$NON-NLS-1$
		footerText = createField(composite,
				Messages.getString("preference.pattern.footer"), configurator.getFooterPattern()); //$NON-NLS-1$
		propertyText = createField(composite,
				Messages.getString("preference.pattern.property"), configurator.getPropertyPattern()); //$NON-NLS-1$
		commentText = createField(composite,
				Messages.getString("preference.pattern.comment"), configurator.getCommentPattern()); //$NON-NLS-1$
		emptyText = createField(composite,
				Messages.getString("preference.pattern.empty"), configurator.getEmptyPattern()); //$NON-NLS-1$

		// Updating the status
		dialogChanged();

		isCreatedPage = true;
		return composite;
	}

	/**
	 * Creates the composite which will contain all the preference controls for
	 * this page.
	 * @param parent the parent composite
	 * @return the composite for this page
	 */
	private Composite createComposite(final Composite parent)
	{
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout(2, false);
		layout.numColumns = 2;
		layout.verticalSpacing = 5;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return composite;
	}

	private void dialogChanged()
	{
		if (headerText.getText().contains(AbstractConfigurator.DELIM)
				|| footerText.getText().contains(AbstractConfigurator.DELIM)
				|| propertyText.getText().contains(AbstractConfigurator.DELIM)
				|| commentText.getText().contains(AbstractConfigurator.DELIM)
				|| emptyText.getText().contains(AbstractConfigurator.DELIM))
		{
			setErrorMessage(Messages.getString("preference.pattern.error.invalid.prefix") + AbstractConfigurator.DELIM + Messages.getString("preference.pattern.error.invalid.suffix")); //$NON-NLS-1$ //$NON-NLS-2$
			setValid(false);
			return;
		}
		setErrorMessage(null);
		setValid(true);
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

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 */
	@Override
	public boolean performOk()
	{
		if (isCreatedPage)
		{
			configurator.setHeaderPattern(headerText.getText());
			configurator.setFooterPattern(footerText.getText());
			configurator.setPropertyPattern(propertyText.getText());
			configurator.setCommentPattern(commentText.getText());
			configurator.setEmptyPattern(emptyText.getText());
		}
		return super.performOk();
	}
}
