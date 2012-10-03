package hu.skzs.multiproperties.handler.text.preference;

import hu.skzs.multiproperties.handler.text.Messages;
import hu.skzs.multiproperties.handler.text.configurator.AbstractConfigurator;

import java.lang.reflect.Method;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.WorkbenchEncoding;
import org.eclipse.ui.ide.dialogs.AbstractEncodingFieldEditor;

/**
 * The {@link EncodingFieldEditor} is a field editor for editing the encoding of
 * the output text files.
 * <p>Refactored from <code>org.eclipse.ui.ide.dialogs.ResourceEncodingFieldEditor</code> class.</p>
 * @author sallai
 */
public class EncodingFieldEditor extends AbstractEncodingFieldEditor
{
	private final AbstractConfigurator configurator;

	public EncodingFieldEditor(final AbstractConfigurator configurator, final Composite parent)
	{
		super();
		this.configurator = configurator;
		setGroupTitle(Messages.getString("preference.output.encoding.title")); //$NON-NLS-1$
		createControl(parent);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.ide.dialogs.AbstractEncodingFieldEditor#defaultButtonText()
	 */
	@Override
	protected String defaultButtonText()
	{
		return Messages.getString("preference.output.encoding.default") //$NON-NLS-1$
				+ WorkbenchEncoding.getWorkbenchDefaultEncoding();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.ide.dialogs.AbstractEncodingFieldEditor#getStoredValue()
	 */
	@Override
	protected String getStoredValue()
	{
		return configurator.getEncodingPattern();
	}

	/**
	 * Return a boolean value whether the default encoding is selected or not.
	 * <p><strong>Note:</strong> because the <code>isDefaultSelected</code> method of {@link AbstractEncodingFieldEditor}
	 * is package protected, this method uses reflection to call it.</p>
	 * @return a boolean value whether the default encoding is selected or not
	 */
	public boolean callIsDefaultSelected()
	{
		try
		{
			final Method method = AbstractEncodingFieldEditor.class
					.getDeclaredMethod("isDefaultSelected", new Class[0]); //$NON-NLS-1$
			method.setAccessible(true);
			return (Boolean) method.invoke(this, new Object[0]);
		}
		catch (final Exception e)
		{
			// TODO: instead of printStackTrace something else should be here
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditor#doStore()
	 */
	@Override
	protected void doStore()
	{
		if (callIsDefaultSelected())
			configurator.setEncodingPattern(null);
		else
			configurator.setEncodingPattern(getSelectedEncoding());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditor#store()
	 */
	@Override
	public void store()
	{
		// Override the store method as we are not using a preference store
		doStore();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditor#load()
	 */
	@Override
	public void load()
	{
		// Override the load method as we are not using a preference store
		setPresentsDefaultValue(false);
		doLoad();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditor#loadDefault()
	 */
	@Override
	public void loadDefault()
	{
		// Override the loadDefault method as we are not using a preference store
		setPresentsDefaultValue(true);
		doLoadDefault();
		refreshValidState();
	}

}
