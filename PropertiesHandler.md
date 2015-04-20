# Introduction #
_Properties_ are configuration values managed as _key/value pairs_. In each pair, the key and value are both String values. The key identifies, and is used to retrieve, the value, much as a variable name is used to retrieve the variable's value. This format is frequently used for configurable parameters of an application, or [backing a ResourceBundle](http://docs.oracle.com/javase/tutorial/i18n/resbundle/propfile.html).

# Handler #
The **Java Properties** handler let you produce `properties` files from the MultiProperties columns with the following features:
  * Includes the `native2ascii` conversion. The user don't need to care about it.
  * All of the [record types](Records.md) are supported. In addition of property records, the comment and empty records result more readable output.
  * Possibility to include the MultiProperties description as comment in the beginning of the output `properties` file.
  * Possibility to include the current column's description as comment in the beginning of the output `properties` file.
  * Possibility to write disabled records as comments instead of skipping them.
  * Possibility to disable the [default values](Records#Property.md).
  * Specifying the output `properties` file.

# Importer #
The **Java Properties** handler is an importer too. So you can easily import content from existing `properties` files by the [importer feature](ImportingContent.md).

# Configuration format #
The `multiproperties` file format provides only a string value for the handler configuration. In some cases, like [command line usage](CommandLineHandlerExecutor.md), you need to know how this value is encoded. In **Java Properties** handler case it is the following.

The target `properties` file together with the above detailed options are appended together with `|` separator character. The stored values in order are:
  1. File name of the output `properties` file (can be fully qualified path).
  1. Flag to include MultiProperties description as comment in the beginning of the output properties file. Only `true` and `false` are valid values.
  1. Flag to include the selected column description as comment in the beginning of the output properties file. Only `true` and `false` are valid values.
  1. Flag to include disabled property records as comment. Only `true` and `false` are valid values.
  1. Flag to disable the [default values](Records#Property.md). Only `true` and `false` are valid values.
Example:
> `c:\EN.properties|true|true|true|false`