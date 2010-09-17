package com.ind.multiproperties.ui.editors;

import java.util.StringTokenizer;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.ind.multiproperties.base.model.AbstractRecord;
import com.ind.multiproperties.base.model.CommentRecord;
import com.ind.multiproperties.base.model.PropertyRecord;
import com.ind.multiproperties.ui.Activator;

public class EditorLabelProvider extends LabelProvider implements ITableLabelProvider, IColorProvider
{

	private final Editor editor;

	public EditorLabelProvider(final Editor editor)
	{
		super();
		this.editor = editor;
	}

	public String getColumnText(final Object obj, final int index)
	{
		final AbstractRecord record = (AbstractRecord) obj;
		if (record instanceof PropertyRecord)
		{
			final PropertyRecord propertyrecord = (PropertyRecord) record;
			if (index == 0)
				return propertyrecord.getValue();
			else
			{
				if (propertyrecord.getColumnValue(editor.getTable().getColumns().get(index - 1)) == null)
					return "";
				else
					return propertyrecord.getColumnValue(editor.getTable().getColumns().get(index - 1));
			}
		}
		else if (record instanceof CommentRecord && index == 0)
		{
			final CommentRecord commentrecord = (CommentRecord) record;
			return commentrecord.getValue();
		}
		else
			return "";
	}

	private Image createDecoratedImage(final Image base, final Image decorator, final int quadrant)
	{
		final ImageDescriptor descriptor = ImageDescriptor.createFromImage(decorator);
		final DecorationOverlayIcon icon = new DecorationOverlayIcon(base, descriptor, quadrant);
		return icon.createImage();
	}

	public Image getColumnImage(final Object obj, final int index)
	{
		if (index == 0)
		{
			final ImageRegistry registry = Activator.getDefault().getImageRegistry();
			final AbstractRecord record = (AbstractRecord) obj;
			if (record instanceof PropertyRecord)
			{
				final PropertyRecord propertyrecord = (PropertyRecord) record;
				if (propertyrecord.isDisabled() && !propertyrecord.isDuplicated())
				{
					if (registry.get("record_disabled") == null)
						registry.put("record_disabled", createDecoratedImage(registry.get("record"), registry.get("disabled"), IDecoration.BOTTOM_LEFT));
					return registry.get("record_disabled");
				}
				else if (!propertyrecord.isDisabled() && propertyrecord.isDuplicated())
				{
					if (registry.get("record_warning") == null)
						registry.put("record_warning", createDecoratedImage(registry.get("record"), registry.get("warning"), IDecoration.BOTTOM_RIGHT));
					return registry.get("record_warning");
				}
				else if (propertyrecord.isDisabled() && propertyrecord.isDuplicated())
				{
					if (registry.get("record_disabled_warning") == null)
					{
						Image image = createDecoratedImage(registry.get("record"), registry.get("disabled"), IDecoration.BOTTOM_LEFT);
						image = createDecoratedImage(image, registry.get("warning"), IDecoration.BOTTOM_RIGHT);
						registry.put("record_disabled_warning", image);
					}
					return registry.get("record_disabled_warning");
				}
				else
					return registry.get("record");
			}
			else if (record instanceof CommentRecord)
				return registry.get("comment");
			else
				return registry.get("empty");
		}
		else
			return null;
	}

	@Override
	public Image getImage(final Object obj)
	{
		return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
	}

	private Color parseColor(final String value)
	{
		final StringTokenizer st = new StringTokenizer(value, ",");
		return new Color(Display.getDefault(), new RGB(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
	}

	public Color getForeground(final Object element)
	{
		final IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		final AbstractRecord record = (AbstractRecord) element;
		if (record instanceof CommentRecord)
			return parseColor(store.getString("color_comment_foreground"));
		else if (record instanceof PropertyRecord)
		{
			final PropertyRecord propertyrecord = (PropertyRecord) record;
			if (propertyrecord.isDisabled())
				return parseColor(store.getString("color_property_disengaged_foreground"));
			else
				return parseColor(store.getString("color_property_foreground"));
		}
		else
			return null;
	}

	public Color getBackground(final Object element)
	{
		final IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		final AbstractRecord record = (AbstractRecord) element;
		if (record instanceof CommentRecord)
			return parseColor(store.getString("color_comment_background"));
		else if (record instanceof PropertyRecord)
		{
			return parseColor(store.getString("color_property_background"));
		}
		else
			return parseColor(store.getString("color_empty_background"));
	}
}
