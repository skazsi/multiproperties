Previous step: [How to implement new handler configurator class](ImplementingNewHandlerConfiguratorClass.md)


---


# Introduction #
Because we just need to use the classes implemented at the [previous step](ImplementingNewHandlerConfiguratorClass.md), now the only step left is the proper implementation of the handler class. This class is responsible for providing the actual content for the file.

# Handler class #
Update the `TestHandler` class as follows.

```java
package hu.skzs.multiproperties.handler.test;

import hu.skzs.multiproperties.base.api.HandlerException;
import hu.skzs.multiproperties.base.api.IHandler;
import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.handler.test.configurator.AbstractConfigurator;
import hu.skzs.multiproperties.handler.test.configurator.ConfiguratorFactory;
import hu.skzs.multiproperties.handler.test.writer.IWriter;
import hu.skzs.multiproperties.handler.test.writer.WriterFactory;

public class TestHandler implements IHandler
{

private static final String MARKER_NAME = "${name}";
private static final String MARKER_DESCRIPTION = "${description}";
private static final String MARKER_COLUMNNAME = "${columnName}";
private static final String MARKER_COLUMNDESCRIPTION = "${columnDescription}";
private static final String MARKER_KEY = "${key}";
private static final String MARKER_VALUE = "${value}";

public void save(final String configuration, final Table table, final Column column) throws HandlerException
{
final AbstractConfigurator configurator = ConfiguratorFactory.getConfigurator(configuration);
final IWriter writer = WriterFactory.getWriter(configurator);
writer.write(convert(configurator, table, column));
}

public byte[] convert(final AbstractConfigurator configuration, final Table table, final Column column)
{
try
{
final StringBuilder strb = new StringBuilder();
strb.append(replaceMarkers(configuration.getHeaderPattern(), table, column, null));
for (int i = 0; i < table.size(); i++)
{
final AbstractRecord record = table.get(i);
String pattern = null;
if (record instanceof PropertyRecord)
{
final PropertyRecord propertyRecord = (PropertyRecord) record;
if (propertyRecord.isDisabled())
continue;
pattern = configuration.getPropertyPattern();
}
else if (record instanceof CommentRecord)
pattern = configuration.getCommentPattern();
else
pattern = configuration.getEmptyPattern();
strb.append(replaceMarkers(pattern, table, column, record));
}
strb.append(replaceMarkers(configuration.getFooterPattern(), table, column, null));
return strb.toString().getBytes("UTF-8");
}
catch (final Exception e)
{
throw new HandlerException("Unable to produce the content", e);
}
}

private String replaceMarkers(final String pattern, final Table table, final Column column,
final AbstractRecord record)
{
String replaced = pattern.replace(MARKER_NAME, table.getName());
replaced = replaced.replace(MARKER_DESCRIPTION, table.getDescription());
replaced = replaced.replace(MARKER_COLUMNNAME, column.getName());
replaced = replaced.replace(MARKER_COLUMNDESCRIPTION, column.getDescription());
if (record != null)
{
String key = null;
String value = null;
if (record instanceof PropertyRecord)
{
final PropertyRecord propertyRecord = (PropertyRecord) record;
key = propertyRecord.getValue();
value = propertyRecord.getColumnValue(column);
if (value == null)
value = propertyRecord.getDefaultColumnValue();
}
else if (record instanceof CommentRecord)
{
final CommentRecord commentRecord = (CommentRecord) record;
value = commentRecord.getValue();
}
if (key != null)
replaced = replaced.replace(MARKER_KEY, key);
if (value != null)
replaced = replaced.replace(MARKER_VALUE, value);
}
return replaced;
}
}
```

# Testing #

If you launch the test Eclipse again, then the MultiProperties plug-in can save the content according to the handler configuration. If you set the patterns according to the [first page of the tutorial](ImplementingNewHandlerTutorial#Markers.md), then the handler will produce our text files properly.

## Command line testing ##

You need to follow the [command line](CommandLineHandlerExecutor.md) wiki page to test the new handler from command line. Please note that the handler configuration's `path` part must be file system based instead of Eclipse workspace.


---


Previous step: [How to implement new handler configurator class](ImplementingNewHandlerConfiguratorClass.md)