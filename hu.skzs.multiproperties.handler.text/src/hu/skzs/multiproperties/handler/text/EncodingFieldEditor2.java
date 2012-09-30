package hu.skzs.multiproperties.handler.text;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.WorkbenchEncoding;
import org.eclipse.ui.ide.dialogs.AbstractEncodingFieldEditor;

public class EncodingFieldEditor2 extends AbstractEncodingFieldEditor
{

	public EncodingFieldEditor2(final Composite parent)
	{
		setLabelText("labelText");
		setGroupTitle("groupTitle");
		createControl(parent);
	}

	@Override
	protected String defaultButtonText()
	{
		return "Workbench Default: " + WorkbenchEncoding.getWorkbenchDefaultEncoding();
	}

	@Override
	protected String getStoredValue()
	{
		return WorkbenchEncoding.getWorkbenchDefaultEncoding();
	}

	@Override
	protected void doStore()
	{

	}

}
