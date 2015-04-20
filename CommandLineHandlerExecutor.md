The handlers can be used from command line too. That means you can take any `multiproperties` file as input, then convert one or all of it's columns to the desired output by choosing any of your available handler.

# Preparation #
First of all you need to collect the _base_ plugin `jar` file of the MultiProperties feature together with the desired handler plugin `jar` file. Hereinafter the examples below will use the **Java Properties** handler, so finally you need to collect the following `jar` files from your Eclipse installation's `plugin` folder.
  * `hu.skzs.multiproperties.base_X.Y.Z.jar`
  * `hu.skzs.multiproperties.handler.java_X.Y.Z.jar`
Please note that the `X`, `Y` and `Z` means the major, minor and bugfix version numbers.  In the following the `1.2.0` version is used.

If your handler requires some additional `jar` files on the classpath, you need to take care of them too.

# Printing the usage #
Enter the following command to print out the usage.
> <pre>java -classpath hu.skzs.multiproperties.base_1.2.0.jar;hu.skzs.multiproperties.handler.java_1.2.0.jar hu.skzs.multiproperties.base.HandlerExecutor</pre>
It will produce the following output.
<pre>
MultiProperties Handler Executor<br>
Usage:<br>
for transforming only one column:<br>
<-file MultiPropertiesFilePath> <-columnName ColumnName> [-columnConfig ColumnConfig] <-handlerClass HandlerClass><br>
for transforming all columns:<br>
<-file MultiPropertiesFilePath> <-columnConfigPattern ColumnConfigPattern> <-handlerClass HandlerClass><br>
help<br>
-help<br>
Where:<br>
file                : Path to the MultiProperties file.<br>
columnName          : Name of the column to be used by the handler.<br>
columnConfig        : The handler specific configuration to be used.<br>
When neither of this and 'columnConfigPattern' is set,<br>
then the stored value by MultiProperties file is used.<br>
Cannot be used together with 'columnConfigPattern'.<br>
columnConfigPattern : The handler specific configuration pattern to be used.<br>
When used, all of the columns will be transformed. It's value can<br>
contain ${columnName} marker which will be replaced to the current column.<br>
Cannot be used together with 'columnConfig'.<br>
handlerClass        : Fully qualified class name of the handler.<br>
help                : Prints this help out.<br>
Example:<br>
-file c:\file.multiproperties -columnName EN -columnConfig c:\EN.properties|true|true|true -handlerClass hu.skzs.multiproperties.handler.java.JavaHandle<br>
r<br>
or<br>
-file c:\file.multiproperties -columnConfigPattern c:\${columnName}.properties|true|true|true -handlerClass hu.skzs.multiproperties.handler.java.JavaHan<br>
dler<br>
</pre>

# Converting a column #
Enter the following command to convert for example the `file.multiproperties` file's **EN** called column into `EN.properties` file with the **Java Properties** handler.
> <pre>java -classpath hu.skzs.multiproperties.base_1.2.0.jar;hu.skzs.multiproperties.handler.java_1.2.0.jar hu.skzs.multiproperties.base.HandlerExecutor -file file.multiproperties -columnName EN -columnConfig "EN.properties|true|true|true|false" -handlerClass hu.skzs.multiproperties.handler.java.JavaHandler</pre>
It will produce the desired `EN.properties` file. Please see the [Java Properties configuration](PropertiesHandler#Configuration_format.md) format to understand the boolean values in the end of the column configuration.

# Converting all of the columns #
<sup>Since 1.2.0</sup>
Enter the following command to convert for example the `file.multiproperties` file's each column into different properties files with the **Java Properties** handler.
> <pre>java -classpath hu.skzs.multiproperties.base_1.2.0.jar;hu.skzs.multiproperties.handler.java_1.2.0.jar hu.skzs.multiproperties.base.HandlerExecutor -file file.multiproperties -columnConfigPattern "messages_${columnName}.properties|true|true|true|false" -handlerClass hu.skzs.multiproperties.handler.java.JavaHandler</pre>
If the input `file.multiproperties` contained two columns with `EN` and `HU` names, then two properties file will be generated with `messages_EN.properties` and `messages_HU.properties` names. Please see the [Java Properties configuration](PropertiesHandler#Configuration_format.md) format to understand the boolean values in the end of the column configuration.

# Using line break #
<sup>Since 1.2.0</sup>
The MultiProperties command line interpreter automatically replaces the `\n` character sequence to a line break. If you would like to disable this feature, then set the `disableLineBreak=true` VM argument. Example:
> <pre>java -DdisableLineBreak=true -classpath hu.skzs.multiproperties.base_1.2.0.jar; ...</pre>

# Additional examples #
## Using Text File Handler ##
If you would like to use the **Text File** handler instead of **Java Properties** handler, then at first you need to put its `jar` file on the classpath which is the `hu.skzs.multiproperties.handler.text_X.Y.Z.jar`. Furthermore you always need to use handler specific configuration.

Because the **Text File** handler is an advanced version of [How to implement new handler](ImplementingNewHandlerTutorial.md) tutorial, if you would like to produce such SQL files which are described in the [How to implement new handler configurator class](ImplementingNewHandlerConfiguratorClass.md) part of the tutorial, then you need to use the following command.

<pre>java -classpath hu.skzs.multiproperties.base_1.2.0.jar;hu.skzs.multiproperties.handler.text_1.2.0.jar hu.skzs.multiproperties.base.HandlerExecutor -file file.multiproperties -columnConfigPattern "${columnName}.sql/#/UTF-8/#/${description}\n/#/${key} - ${value}\n/#/${value}\n/#/\n/#//#/ETX" -handlerClass hu.skzs.multiproperties.handler.text.TextHandler</pre>

Please note that the exact format of the **Text File** handler configuration and the used format by [How to implement new handler](ImplementingNewHandlerTutorial.md) tutorial is slightly different. The tutorial does support the output file encoding. For proper description please see the [Text File configuration](TextFileHandler#Configuration_format.md) format to understand it.

# Ant/Maven build process #
While the MultiProperties does not support directly the And and Maven build processes, until both of them give the possibility to call any Java application as a build step. This chapter is finally just a hint instead of a detailed description.