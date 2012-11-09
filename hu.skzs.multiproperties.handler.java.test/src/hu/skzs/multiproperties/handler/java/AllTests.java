package hu.skzs.multiproperties.handler.java;

import hu.skzs.multiproperties.handler.java.configurator.ConfiguratorTests;
import hu.skzs.multiproperties.handler.java.importer.ImporterTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ JavaHandlerTest.class, ConfiguratorTests.class, ImporterTests.class })
public class AllTests
{

}
