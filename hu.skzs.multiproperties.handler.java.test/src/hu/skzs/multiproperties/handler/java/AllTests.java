package hu.skzs.multiproperties.handler.java;

import hu.skzs.multiproperties.handler.java.importer.ImporterTests;
import hu.skzs.multiproperties.handler.java.writer.WriterTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ JavaHandlerTest.class, WriterTests.class, ImporterTests.class })
public class AllTests
{

}
