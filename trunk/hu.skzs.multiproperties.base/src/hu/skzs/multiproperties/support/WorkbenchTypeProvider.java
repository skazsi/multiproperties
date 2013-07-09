package hu.skzs.multiproperties.support;

/**
 * The {@link WorkbenchTypeProvider} provides information about the current workbench type whether
 * it is a stand-alone RCP application or Eclipse SDK.
 * 
 * <p>The default value is the Eclipse SDK, and it is the stand-alone version responsibility to change the
 * internal state of this provider.</p>
 * 
 * @author skzs
 */
public class WorkbenchTypeProvider
{
	protected static boolean isStandAlone;

	/**
	 * Returns a boolean value whether the current workbench is stand-alone or not.
	 * @return a boolean value whether the current workbench is stand-alone or not
	 */
	public static boolean isStandAlone()
	{
		return isStandAlone;
	}

	/**
	 * Sets the internal state of the provider whether the current workbench is stand-alone or not.
	 * <p><strong>Note:</strong> clients should not call this method.</p>
	 * @param standAlone the given value to be set
	 */
	public static void setStandAlone(final boolean standAlone)
	{
		isStandAlone = standAlone;
	}

}
