package hu.skzs.multiproperties.handler.text;

import hu.skzs.multiproperties.handler.text.configurator.ConfiguratorTests;
import hu.skzs.multiproperties.handler.text.writer.WriterTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ConfiguratorTests.class, WriterTests.class, TextHandlerTest.class })
public class AllTests
{

}
