package hu.skzs.multiproperties.base.registry.element;

import org.eclipse.core.runtime.IConfigurationElement;

/**
 * The {@link AbstractRegistryElement} is the base implementation of registry elements.
 * @author sallai
 *
 */
public abstract class AbstractRegistryElement
{

	protected String name;
	protected IConfigurationElement configurationElement;

	/**
	 * Sets the name.
	 * @param name the given name
	 */
	public void setName(final String name)
	{
		this.name = name;
	}

	/**
	 * Returns the name
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the configuration element
	 * @param configurationElement the given configuration element
	 */
	public void setConfigurationElement(final IConfigurationElement configurationElement)
	{
		this.configurationElement = configurationElement;
	}
}
