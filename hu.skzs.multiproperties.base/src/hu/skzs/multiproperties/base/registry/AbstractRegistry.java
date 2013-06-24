package hu.skzs.multiproperties.base.registry;

import hu.skzs.multiproperties.base.Activator;
import hu.skzs.multiproperties.base.registry.element.AbstractRegistryElement;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * The {@link AbstractRegistry} is the base implementation for all extension registry.
 * @author skzs
 *
 * @param <T>
 */
public abstract class AbstractRegistry<T extends AbstractRegistryElement>
{

	private final List<T> elements = new ArrayList<T>();

	/**
	 * Returns the identifier of the extension point.
	 * @return the identifier of the extension point
	 */
	public abstract String getExtensionPointId();

	/**
	 * Returns a newly built element based on the given <code>configurationElement</code>.
	 * @param configurationElement the given configuration element
	 * @return a newly built element
	 * @throws CoreException
	 */
	public abstract T getRegistryElement(IConfigurationElement configurationElement) throws CoreException;

	/**
	 * Inner comparator class for ordering the elements. 
	 */
	private final Comparator<T> comparator = new Comparator<T>()
	{
		private final Collator collator = Collator.getInstance();

		public int compare(final T element1, final T element2)
		{
			return collator.compare(element1.getName(), element2.getName());
		}
	};

	/**
	 * Initializes the registry.
	 * <p>Loads all of the extensions specified by the returned value of
	 * {@link #getExtensionPointId()} method, and sort them.</p>
	 */
	protected void initialize()
	{
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IConfigurationElement[] configurationElements = registry
				.getConfigurationElementsFor(getExtensionPointId());

		for (final IConfigurationElement element : configurationElements)
		{
			try
			{
				final T registryElement = getRegistryElement(element);
				elements.add(registryElement);
			}
			catch (final CoreException e)
			{
				Activator.logError("Unexpected error occurred during parsing the extension element", e); //$NON-NLS-1$
			}
		}
		Collections.sort(elements, comparator);
	}

	/**
	 * Returns the list of registry elements.
	 * @return the list of registry elements
	 */
	public List<T> getElements()
	{
		return elements;
	}

	/**
	 * Returns the first element with name equals to the given name.
	 * @param name the given name
	 * @return the first element with name equals to the given name
	 */
	public T getElementByName(final String name)
	{
		for (final T element : elements)
		{
			if (element.getName().equals(name))
			{
				return element;
			}
		}
		return null;
	}
}
