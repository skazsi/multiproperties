package hu.skzs.multiproperties.base;

import hu.skzs.multiproperties.base.io.InputStreamContentReaderTest;
import hu.skzs.multiproperties.base.model.ModelTests;
import hu.skzs.multiproperties.base.model.fileformat.SchemaConverterFactoryTest;
import hu.skzs.multiproperties.base.parameter.ParametersTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ModelTests.class, SchemaConverterFactoryTest.class, InputStreamContentReaderTest.class,
		hu.skzs.multiproperties.base.model.fileformat_1_0.SchemaConverterTest.class,
		hu.skzs.multiproperties.base.model.fileformat_1_1.SchemaConverterTest.class, ParametersTest.class })
public class AllTests
{

}
