package hu.skzs.multiproperties.ui.util;

import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * The {@link ComboPart} class is a wrapper for either a {@link Combo} or a {@link CCombo} class depending on
 * the {@link FormToolkit#getBorderStyle()} method.
 * @author skzs
 * @see FormToolkit#getBorderStyle()
 */
public class ComboPart
{
	protected Control combo;

	public void addSelectionListener(final SelectionListener listener)
	{
		if ((this.combo instanceof Combo))
			((Combo) this.combo).addSelectionListener(listener);
		else
			((CCombo) this.combo).addSelectionListener(listener);
	}

	public int indexOf(final String item)
	{
		if ((this.combo instanceof Combo))
		{
			return ((Combo) this.combo).indexOf(item);
		}
		return ((CCombo) this.combo).indexOf(item);
	}

	public void addModifyListener(final ModifyListener listener)
	{
		if ((this.combo instanceof Combo))
			((Combo) this.combo).addModifyListener(listener);
		else
			((CCombo) this.combo).addModifyListener(listener);
	}

	public void createControl(final Composite parent, final FormToolkit toolkit, final int style)
	{
		if (toolkit.getBorderStyle() == 2048)
			this.combo = new Combo(parent, style | 0x800);
		else
			this.combo = new CCombo(parent, style | 0x800000);
		toolkit.adapt(this.combo, true, false);
	}

	public Control getControl()
	{
		return this.combo;
	}

	public int getSelectionIndex()
	{
		if ((this.combo instanceof Combo))
			return ((Combo) this.combo).getSelectionIndex();
		return ((CCombo) this.combo).getSelectionIndex();
	}

	public void add(final String item, final int index)
	{
		if ((this.combo instanceof Combo))
			((Combo) this.combo).add(item, index);
		else
			((CCombo) this.combo).add(item, index);
	}

	public void add(final String item)
	{
		if ((this.combo instanceof Combo))
			((Combo) this.combo).add(item);
		else
			((CCombo) this.combo).add(item);
	}

	public void remove(final int index)
	{
		if ((index < 0) || (index >= getItemCount()))
		{
			return;
		}

		if ((this.combo instanceof Combo))
			((Combo) this.combo).remove(index);
		else
			((CCombo) this.combo).remove(index);
	}

	public void select(final int index)
	{
		if ((this.combo instanceof Combo))
			((Combo) this.combo).select(index);
		else
			((CCombo) this.combo).select(index);
	}

	public String getSelection()
	{
		if ((this.combo instanceof Combo))
			return ((Combo) this.combo).getText().trim();
		return ((CCombo) this.combo).getText().trim();
	}

	public void setText(final String text)
	{
		if ((this.combo instanceof Combo))
			((Combo) this.combo).setText(text);
		else
			((CCombo) this.combo).setText(text);
	}

	public void setItems(final String[] items)
	{
		if ((this.combo instanceof Combo))
			((Combo) this.combo).setItems(items);
		else
			((CCombo) this.combo).setItems(items);
	}

	public void setEnabled(final boolean enabled)
	{
		if ((this.combo instanceof Combo))
			((Combo) this.combo).setEnabled(enabled);
		else
			((CCombo) this.combo).setEnabled(enabled);
	}

	public int getItemCount()
	{
		if ((this.combo instanceof Combo))
			return ((Combo) this.combo).getItemCount();
		return ((CCombo) this.combo).getItemCount();
	}

	public String[] getItems()
	{
		if ((this.combo instanceof Combo))
			return ((Combo) this.combo).getItems();
		return ((CCombo) this.combo).getItems();
	}

	public String getItem(final int i)
	{
		if ((this.combo instanceof Combo))
			return ((Combo) this.combo).getItem(i);
		return ((CCombo) this.combo).getItem(i);
	}

	public String getText()
	{
		if ((this.combo instanceof Combo))
			return ((Combo) this.combo).getText();
		return ((CCombo) this.combo).getText();
	}

	public void setVisibleItemCount(final int count)
	{
		if ((this.combo instanceof Combo))
			((Combo) this.combo).setVisibleItemCount(count);
		else
			((CCombo) this.combo).setVisibleItemCount(count);
	}
}