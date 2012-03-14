package hu.skzs.multiproperties.base;

import hu.skzs.multiproperties.base.model.ModelTests;
import hu.skzs.multiproperties.base.model.fileformat_1_0.SchemaConverterTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ModelTests.class, SchemaConverterTest.class })
public class AllTests
{

}
