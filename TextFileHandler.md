# Introduction #
The **Text File** handler is able to produce almost any kind of text files from the content of columns where each record can be represented with one line in the output text file.

# Handler #

Each of the [record types](Records.md) has an editable associated pattern. When the output is written, then these patterns are written line by line, but to make the result dynamic you can embed different markers into the patterns. For example we can associate the `${key} is famous about ${}` pattern to the property record type. Considering only the following two property records...

| **Key** | **Given column** |
|:--------|:-----------------|
| Hungary | pálinka |
| Japan | sake |

... the handler will generate the following two lines.
```
Hungary is famous about pálinka.
Japan is famous about sake.
```

## Patterns ##
You can specify the following patterns.
  * _header_ - written in the beginning of the output file
  * _footer_ - written in the end of the output file
  * _property_ - written in the output file when the next record is a [property record](Records#Property.md)
  * _comment_ - written in the output file when the next record is a [comment record](Records#Comment.md)
  * _empty_ - written in the output file when the next record is a [empty record](Records#Empty.md)

See below an example screenshot about the pattern configuration.
![http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/wiki/Handlers/TextHandlerConf01.jpg](http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/wiki/Handlers/TextHandlerConf01.jpg)

## Markers ##
You can use the following markers:
  * `${name}` - [Name of the MultiProperties](FeaturesOfEditor#Overview.md)
  * `${description}` - [Description of the MultiProperties](FeaturesOfEditor#Overview.md)
  * `${columnName}` - [Name of the given column](FeaturesOfEditor#Columns.md)
  * `${columnDescription}` - [Description of the given column](FeaturesOfEditor#Columns.md)
  * `${key}` - Key of the [property record](Records#Property.md). Resolved only in property pattern.
  * `${value}` - The column associated value in case of [property record](Records#Property.md), or the value of the comment in case of [comment record](Records#Comment.md).

# Importer #
The **Text File** handler currently does not support importing features.

# Configuration format #
The `multiproperties` file format provides only a string value for the handler configuration. In some cases, like [command line usage](CommandLineHandlerExecutor.md), you need to know how this value is encoded. In **Text File** handler case it is the following.

The target text file together with the above detailed patterns are appended together with `/#/` separator character sequence. The stored values in order are:
  1. File name of the output text file (can be fully qualified path).
  1. The encoding to be used, like `UTF-8`, `ISO-8859-1`, `Cp1250`, etc. When empty, the Eclipse workspace default encoding (or JVM default encoding in case command line usage) is used.
  1. The _header_ pattern containing the possible markers.
  1. The _footer_ pattern containing the possible markers.
  1. The _property_ pattern containing the possible markers.
  1. The _comment_ pattern containing the possible markers.
  1. The _empty_ pattern containing the possible markers.

Example:
> `c:\output.txt/#/UTF-8/#/${description}\n/#/${key} - ${value}\n/#/${value}\n/#/\n/#//#/ETX`

# Known limiations #
The handler cannot handle properly such [descriptions](FeaturesOfEditor#Overview.md) and [column descriptions](FeaturesOfEditor#Columns.md) which consists of multiple lines. Please consider this limitation when using the `${description}` or `${columnDescription}` markers.