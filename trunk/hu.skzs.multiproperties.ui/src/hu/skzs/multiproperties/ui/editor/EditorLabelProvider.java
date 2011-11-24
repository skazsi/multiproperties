package hu.skzs.multiproperties.ui.editor;

import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.preferences.PreferenceConstants;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * The <code>EditorLabelProvider</code> is a standard label provider for the table page.
 * @author sallai
 */
public class EditorLabelProvider extends LabelProvider implements ITableLabelProvider, IColorProvider
{

	/**
	 * The <code>table</code> member hold a reference to the {@link Table} instance.
	 */
	private final Table table;

	/**
	 * Default constructor.
	 * @param table the given {@link Table} instance
	 */
	public EditorLabelProvider(final Table table)
	{
		super();
		this.table = table;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
	 */
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
				if (propertyrecord.getColumnValue(table.getColumns().get(index - 1)) == null)
					return ""; //$NON-NLS-1$
				else
					return propertyrecord.getColumnValue(table.getColumns().get(index - 1));
			}
		}
		else if (record instanceof CommentRecord && index == 0)
		{
			final CommentRecord commentrecord = (CommentRecord) record;
			return commentrecord.getValue();
		}
		else
			return ""; //$NON-NLS-1$
	}

	/**
	 * Return a new {@link Image} instance by decorating the given <code>base</code> image by the given
	 * <code>decorator</code> image in the given <code>quadrant</code>.
	 * @param base
	 * @param decorator
	 * @param quadrant
	 * @return a new {@link Image} instance
	 */
	private Image createDecoratedImage(final Image base, final Image decorator, final int quadrant)
	{
		final ImageDescriptor descriptor = ImageDescriptor.createFromImage(decorator);
		final DecorationOverlayIcon icon = new DecorationOverlayIcon(base, descriptor, quadrant);
		return icon.createImage();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
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

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(final Object obj)
	{
		return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
	}

	/**
	 * Returns a newly constructed {@link Color} instance based on the given <code>value</code> holding a <code>R,G,B</code> value.
	 * @param value the given <code>R,G,B</code> value
	 * @return a newly constructed {@link Color}
	 */
	private Color parseColor(final String value)
	{
		// TODO: The created Color instances should be disposed after the usage. Some color registry should be used maybe.
		return new Color(Display.getDefault(), StringConverter.asRGB(value));
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IColorProvider#getForeground(java.lang.Object)
	 */
	public Color getForeground(final Object element)
	{
		final IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		final AbstractRecord record = (AbstractRecord) element;
		if (record instanceof CommentRecord)
			return parseColor(store.getString(PreferenceConstants.COLOR_COMMENT_FOREGROUND));
		else if (record instanceof PropertyRecord)
		{
			final PropertyRecord propertyrecord = (PropertyRecord) record;
			if (propertyrecord.isDisabled())
				return parseColor(store.getString(PreferenceConstants.COLOR_DISABLED_PROPERTY_FOREGROUND));
			else
				return parseColor(store.getString(PreferenceConstants.COLOR_PROPERTY_FOREGROUND));
		}
		else
			return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IColorProvider#getBackground(java.lang.Object)
	 */
	public Color getBackground(final Object element)
	{
		// Returning null means the default background color
		return null;
	}
}
