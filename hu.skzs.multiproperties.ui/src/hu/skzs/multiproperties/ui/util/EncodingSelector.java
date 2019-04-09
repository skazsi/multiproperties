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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
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
		group.setLayout(new GridLayout(2, false));

		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
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

		defaultEncodingButton = new Button(group, SWT.RADIO);
		defaultEncodingButton.setText(getDefaultEncodingLabel() + getDefaultEncodingValue());
		defaultEncodingButton.setSelection(true);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		defaultEncodingButton.setLayoutData(gridData);
		defaultEncodingButton.addSelectionListener(selectionListener);

		otherEncodingButton = new Button(group, SWT.RADIO);
		otherEncodingButton.setText(getOtherEncodingLabel());
		otherEncodingButton.addSelectionListener(selectionListener);

		encodingCombo = new Combo(group, SWT.NONE);
		gridData = new GridData();
		encodingCombo.setLayoutData(gridData);
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
