package hu.skzs.multiproperties.ui.preferences;

/**
 * The <code>PreferenceConstants</code> contains constant for coloring the MultiProperties
 * table page.
 * @author sallai
 */
public interface PreferenceConstants
{

	/**
	 * The <code>INITIAL_PAGE</code> represents the initial page when the MultiProperties editor is opened. 
	 */
	public static final String INITIAL_PAGE = "initial_page"; //$NON-NLS-1$

	/**
	 * The <code>COLOR_PREFIX</code> is used by all color related constant as prefix value.
	 * @see #COLOR_PROPERTY_FOREGROUND
	 * @see #COLOR_PROPERTY_DEFAULTVALUE_FOREGROUND
	 * @see #COLOR_DISABLED_PROPERTY_FOREGROUND
	 * @see #COLOR_DISABLED_PROPERTY_DEFAULTVALUE_FOREGROUND
	 * @see #COLOR_COMMENT_FOREGROUND
	 */
	public static final String COLOR_PREFIX = "color_"; //$NON-NLS-1$

	/**
	 * The <code>COLOR_PROPERTY_FOREGROUND</code> represents the enabled property's foreground color. 
	 */
	public static final String COLOR_PROPERTY_FOREGROUND = COLOR_PREFIX + "property_foreground"; //$NON-NLS-1$

	/**
	 * The <code>COLOR_PROPERTY_DEFAULTVALUE_FOREGROUND</code> represents the enabled property's foreground color with default value. 
	 */
	public static final String COLOR_PROPERTY_DEFAULTVALUE_FOREGROUND = COLOR_PREFIX
			+ "property_defaultvalue_foreground"; //$NON-NLS-1$

	/**
	 * The <code>COLOR_DISABLED_PROPERTY_FOREGROUND</code> represents the disabled property's foreground color. 
	 */
	public static final String COLOR_DISABLED_PROPERTY_FOREGROUND = COLOR_PREFIX + "disabled_property_foreground"; //$NON-NLS-1$

	/**
	 * The <code>COLOR_DISABLED_PROPERTY_DEFAULTVALUE_FOREGROUND</code> represents the disabled property's foreground color with default value. 
	 */
	public static final String COLOR_DISABLED_PROPERTY_DEFAULTVALUE_FOREGROUND = COLOR_PREFIX
			+ "disabled_property_defaultvalue_foreground"; //$NON-NLS-1$

	/**
	 * The <code>COLOR_COMMENT_FOREGROUND</code> represents the comment's foreground color. 
	 */
	public static final String COLOR_COMMENT_FOREGROUND = COLOR_PREFIX + "comment_foreground"; //$NON-NLS-1$

	/**
	 * The <code>OUTLINE_COLUMN_WIDTH</code> represents the outline page's table <code>Column</code> column width. 
	 */
	public static final String OUTLINE_COLUMN_WIDTH = "outline_key_width"; //$NON-NLS-1$

	/**
	 * The <code>OUTLINE_VALUE_WIDTH</code> represents the outline page's table <code>Value</code> column width. 
	 */
	public static final String OUTLINE_VALUE_WIDTH = "outline_value_width"; //$NON-NLS-1$

}
