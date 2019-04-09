package hu.skzs.multiproperties.ui.util;

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.ide.IDEEncoding;

public abstract class EncodingSelector
{

	private Button defaultEncodingButton;
	private Button otherEncodingButton;
	private Combo encodingCombo;

	public EncodingSelector(final Composite parent)
	{
		createControl(parent);
	}

	private void createControl(final Composite parent)
	{
		final Group group = new Group(parent, SWT.NONE);
		group.setText(getTitle());
		final TableWrapLayout tableWrapLayout = new TableWrapLayout();
		tableWrapLayout.numColumns = 2;
		group.setLayout(tableWrapLayout);

		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 100;
		group.setLayoutData(gridData);

		final SelectionAdapter selectionListener = new SelectionAdapter()
		{
			@Override
			public void widgetSelected(final SelectionEvent e)
			{
				updateEncodingState(defaultEncodingButton.getSelection());
				onChange();
			}
		};

		if (getDescription() != null)
		{
			final Label label = new Label(group, SWT.WRAP);
			label.setText(getDescription());
			label.setLayoutData(new TableWrapData(TableWrapData.FILL, TableWrapData.TOP, 1, 2));
		}

		defaultEncodingButton = new Button(group, SWT.RADIO);
		defaultEncodingButton.setText(getDefaultEncodingLabel() + getDefaultEncodingValue());
		defaultEncodingButton.setSelection(true);
		defaultEncodingButton.setLayoutData(new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP, 1, 2));
		defaultEncodingButton.addSelectionListener(selectionListener);

		otherEncodingButton = new Button(group, SWT.RADIO);
		otherEncodingButton.setText(getOtherEncodingLabel());
		otherEncodingButton.addSelectionListener(selectionListener);
		otherEncodingButton.setLayoutData(new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP));

		encodingCombo = new Combo(group, SWT.NONE);
		encodingCombo.setLayoutData(new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP, 1, 1));
		encodingCombo.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(final SelectionEvent e)
			{
				onChange();
			}
		});
		encodingCombo.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(final KeyEvent e)
			{
				onChange();
			}
		});

		final List<String> encodings = IDEEncoding.getIDEEncodings();
		final String[] encodingStrings = new String[encodings.size()];
		encodings.toArray(encodingStrings);
		encodingCombo.setItems(encodingStrings);
		encodingCombo.setText(getDefaultEncodingValue());

		updateEncodingState(defaultEncodingButton.getSelection());
	}

	public abstract String getTitle();

	public abstract String getDescription();

	public abstract String getDefaultEncodingLabel();

	public abstract String getDefaultEncodingValue();

	public abstract String getOtherEncodingLabel();

	public abstract void onChange();

	private void updateEncodingState(final boolean useDefault)
	{
		defaultEncodingButton.setSelection(useDefault);
		otherEncodingButton.setSelection(!useDefault);
		if (useDefault)
		{
			encodingCombo.setText(getDefaultEncodingValue());
		}
		encodingCombo.setEnabled(!useDefault);
	}

	public void setEncoding(final String encoding)
	{
		if (encoding == null || encoding.equals(getDefaultEncodingValue()))
		{
			updateEncodingState(true);
		}
		else
		{
			updateEncodingState(false);
			encodingCombo.setText(encoding);
		}
	}

	public boolean isEncodingValid()
	{
		return defaultEncodingButton.getSelection() || isValidEncoding(encodingCombo.getText());
	}

	public String getEncoding()
	{
		if (isEncodingValid())
			return defaultEncodingButton.getSelection() ? getDefaultEncodingValue() : encodingCombo.getText();
		return null;
	}

	private boolean isValidEncoding(final String enc)
	{
		try
		{
			return Charset.isSupported(enc);
		}
		catch (final IllegalCharsetNameException e)
		{
			return false;
		}
	}
}
