package hu.skzs.multiproperties.ui;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static final String BUNDLE_NAME = "plugin";

	private static final ResourceBundle RESOURCE_BUNDLE;

	static {
		try {
			RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault());
		} catch (final Exception e) {
			Activator.logError(e);
			throw new RuntimeException(e);
		}
	}

	public static ResourceBundle getResourceBundle(final Object object) {
		return RESOURCE_BUNDLE;
	}

	public static String getString(final String key) {
		try {
			return getResourceBundle(key).getString(key);
		} catch (final MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static String getString(final String key, final Object argument) {
		return MessageFormat.format(getString(key), new Object[] { argument });
	}
}
