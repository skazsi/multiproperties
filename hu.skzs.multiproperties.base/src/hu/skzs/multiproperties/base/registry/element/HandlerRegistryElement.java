package hu.skzs.multiproperties.base.registry.element;

import hu.skzs.multiproperties.base.api.IHandler;
import hu.skzs.multiproperties.base.api.IHandlerConfigurator;

import org.eclipse.core.runtime.CoreException;

/**
 * The {@link HandlerRegistryElement} is an implementation of {@link AbstractRegistryElement} and
 * represent a handler extension element.
 * @author SKZS
 *
 */
public class HandlerRegistryElement extends AbstractRegistryElement
{

	/**
	 * Returns a newly created {@link IHandler} instance 
	 * @return a newly created {@link IHandler} instance
	 * @throws CoreException
	 */
	public IHandler getHandler() throws CoreException
	{
		final IHandler handler = (IHandler) configurationElement.createExecutableExtension(IHandler.CLASS);
		return handler;
	}

	/**
	 * Returns a newly created {@link IHandlerConfigurator} instance 
	 * @return a newly created {@link IHandlerConfigurator} instance
	 * @throws CoreException
	 */
	public IHandlerConfigurator getHandlerConfigurator() throws CoreException
	{
		final IHandlerConfigurator handlerConfigurator = (IHandlerConfigurator) configurationElement
				.createExecutableExtension(IHandler.CONFIGURATOR_CLASS);
		return handlerConfigurator;
	}

}
