package hu.skzs.multiproperties.ui.editor;

import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.command.TooltipHandler;
import hu.skzs.multiproperties.ui.preferences.PreferenceConstants;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.RegistryToggleState;

/**
 * The <code>TableColumnLabelProvider</code> is the label provider for the table page.
 * @author skzs
 */
public class TableColumnLabelProvider extends ColumnLabelProvider
{

	/**
	 * The <code>column</code> member hold a reference to the {@link Column} instance.
	 * <p>When it is <code>null</code>, the label provider defines how the <strong>key</strong> column should be displayed.</p>
	 */
	private Column column;

	/**
	 * Constructor for the key column.
	 * <p>The label provider constructed in this way defines how the <strong>key</strong> column should be displayed.</p>
	 */
	public TableColumnLabelProvider()
	{
	}

	/**
	 * Constructor for a value column.
	 * <p>The label provider constructed in this way defines how the given {@link Column} should be displayed.</p>
	 * @param column the given {@link Column} instance
	 */
	public TableColumnLabelProvider(final Column column)
	{
		this.column = column;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(final Object element)
	{
		if (element instanceof PropertyRecord)
		{
			final PropertyRecord propertyRecord = (PropertyRecord) element;
			if (column == null)
				return propertyRecord.getValue();
			else
			{
				// Value
				final String value = propertyRecord.getColumnValue(column);
				if (value != null)
					return value;

				// Default value
				final String defaultValue = propertyRecord.getDefaultColumnValue();
				if (defaultValue != null)
					return defaultValue;

				return ""; //$NON-NLS-1$
			}
		}
		else if (element instanceof CommentRecord && column == null)
		{
			final CommentRecord commentRecord = (CommentRecord) element;
			return commentRecord.getValue();
		}
		else
			return ""; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(final Object element)
	{
		if (column != null)
			return null;

		final ImageRegistry registry = Activator.getDefault().getImageRegistry();
		if (element instanceof PropertyRecord)
		{
			final PropertyRecord propertyRecord = (PropertyRecord) element;
			final String imageName = getImageName(propertyRecord);
			if (registry.get(imageName) == null)
			{
				final Image image = createDecoratedImage(propertyRecord);
				registry.put(imageName, image);
			}
			return registry.get(imageName);
		}
		else if (element instanceof CommentRecord)
			return registry.get("comment"); //$NON-NLS-1$
		else
			return registry.get("empty"); //$NON-NLS-1$
	}

	private String getImageName(final PropertyRecord propertyRecord)
	{
		final StringBuilder imageName = new StringBuilder("record"); //$NON-NLS-1$
		if (propertyRecord.isMultiLine())
		{
			imageName.append("_multiline"); //$NON-NLS-1$
		}
		if (propertyRecord.isDisabled())
		{
			imageName.append("_disabled"); //$NON-NLS-1$
		}
		if (propertyRecord.isDuplicated())
		{
			imageName.append("_duplicated"); //$NON-NLS-1$
		}
		return imageName.toString();
	}

	private Image createDecoratedImage(final PropertyRecord propertyRecord)
	{
		final ImageRegistry registry = Activator.getDefault().getImageRegistry();
		Image image = registry.get("record"); //$NON-NLS-1$
		if (propertyRecord.isMultiLine())
		{
			image = createDecoratedImage(image, registry.get("multiline"), IDecoration.BOTTOM_RIGHT); //$NON-NLS-1$
		}
		if (propertyRecord.isDisabled())
		{
			image = createDecoratedImage(image, registry.get("disabled"), IDecoration.BOTTOM_LEFT); //$NON-NLS-1$
		}
		if (propertyRecord.isDuplicated())
		{
			image = createDecoratedImage(image, registry.get("warning"), IDecoration.TOP_RIGHT); //$NON-NLS-1$
		}
		return image;
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
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getForeground(java.lang.Object)
	 */
	@Override
	public Color getForeground(final Object element)
	{
		final IPreferenceStore store = Activator.getDefault().getPreferenceStore();

		if (element instanceof PropertyRecord)
		{
			final PropertyRecord propertyRecord = (PropertyRecord) element;
			// Value
			if (column == null || propertyRecord.getColumnValue(column) != null)
				if (propertyRecord.isDisabled())
					return parseColor(store.getString(PreferenceConstants.COLOR_DISABLED_PROPERTY_FOREGROUND));
				else
					return parseColor(store.getString(PreferenceConstants.COLOR_PROPERTY_FOREGROUND));

			// Default value
			if (propertyRecord.getDefaultColumnValue() != null)
				if (propertyRecord.isDisabled())
					return parseColor(store
							.getString(PreferenceConstants.COLOR_DISABLED_PROPERTY_DEFAULTVALUE_FOREGROUND));
				else
					return parseColor(store.getString(PreferenceConstants.COLOR_PROPERTY_DEFAULTVALUE_FOREGROUND));

			return null;
		}
		else if (element instanceof CommentRecord && column == null)
		{
			return parseColor(store.getString(PreferenceConstants.COLOR_COMMENT_FOREGROUND));
		}
		else
			return null;
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
	 * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipText(java.lang.Object)
	 */
	@Override
	public String getToolTipText(final Object element)
	{
		if (!(element instanceof PropertyRecord))
			return null;
		final PropertyRecord propertyRecord = (PropertyRecord) element;

		// Checking the tooltip enabled state
		final ICommandService commandService = (ICommandService) PlatformUI.getWorkbench().getService(
				ICommandService.class);
		final Boolean showTooltip = (Boolean) commandService.getCommand(TooltipHandler.COMMAND_ID)
				.getState(RegistryToggleState.STATE_ID).getValue();
		if (!showTooltip)
			return null;

		// Value
		if (column == null)
			return getToolTipText(propertyRecord.getDescription());

		return null;
	}

	/**
	 * Returns the given <code>value</code> if it contains valuable String.
	 * Otherwise, when its trimmed value equals to an empty String <code>null</code> is returned. 
	 * @param value the given String value
	 * @return the given String value or <code>null</code>
	 */
	private String getToolTipText(final String value)
	{
		if ("".equals(value.trim())) //$NON-NLS-1$
			return null;
		return value;
	}
}
