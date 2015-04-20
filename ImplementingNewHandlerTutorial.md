# Introduction #
This tutorial wiki page describes how you can implement a new handler. The description assumes that you are familiar with the Eclipse [plugin development](http://www.eclipse.org/pde/) on basic level together with [SWT](http://www.eclipse.org/swt/) and [JFace](http://wiki.eclipse.org/JFace).

## Test Handler as example ##
By the end of this tutorial we get a new **Test Handler** named handler which is able to produce almost any kind of text files from the content of columns. To be more realistic, lets imagine the followings.

We have a `localization` named database table with the below seen structure.
```sql

CREATE TABLE localization(
key varchar NOT NULL,
lang varchar NOT NULL,
value varchar,
CONSTRAINT pk_localization PRIMARY KEY (key,lang)
);
```
When we save the MultiProperties content having **EN** and **DE** columns, we would like to produce the following SQL file for the **EN** column...
```sql

DELETE localization WHERE lang = 'EN';

-- comment line
INSERT INTO localization VALUES ('fruit.apple', 'EN', 'Apple');
INSERT INTO localization VALUES ('fruit.plum', 'EN', 'Plum');
COMMIT;
```
...and the following SQL file for the **DE** column.
```sql

DELETE localization WHERE lang = 'DE';

-- comment line
INSERT INTO localization VALUES ('fruit.apple', 'DE', 'Apfel');
INSERT INTO localization VALUES ('fruit.plum', 'DE', 'Pflaume');
COMMIT;
```
As a result we can easily refresh the content of `localization` table for each supported language by executing the given language specific SQL script file.

# Required features #
To satisfy the above detailed requirements the following features are needed. First of all the output file encoding should be **UTF-8** in order to support the national/special characters too. Furthermore the output file structure can be split up into 3 major parts, _header_, _body_ and _footer_.

The _header_ and _footer_ can be used for including such contents like the [description of MultiProperties](FeaturesOfEditor#Overview.md) or [description of column](FeaturesOfEditor#Columns.md) as comment, but it can also include the `DELETE` or `COMMIT` statements.

The _body_ part includes the content of the given column. The [property](Records#Property.md) records produce the above seen `INSERT` statements. The [comment](Records#Comment.md) records are written out simply with a preceding `--` sign. Finally the [empty](Records#Empty.md) records result empty lines only.

## Markers ##
The _header_, _footer_ and finally the _body_ which has [property](Records#Property.md), [comment](Records#Comment.md) and [empty](Records#Empty.md) sub elements can be specified with constant values by the handler configuration. But to make the output dynamic, in other word to include actual values into the constant values, the following markers can be used.
  * `description` - [description of MultiProperties](FeaturesOfEditor#Overview.md)
  * `columnName` - [name of column](FeaturesOfEditor#Columns.md)
  * `columnDescription` - [description of column](FeaturesOfEditor#Columns.md)
  * `key` - key value of [property](Records#Property.md) record
  * `value` - value of [property](Records#Property.md) record associated to the given column or value of [comment](Records#Comment.md) record
The marker format is started with `${` characters, followed by the name of marker, and ended with `}` character. Example: `${description}`.

According to the above seen specification, we will use the following patterns in order to get our desired SQL files.
| **Pattern** | **Value** |
|:------------|:----------|
| Header | `DELETE localization WHERE lang = '${columnName}';` |
| Footer | `COMMIT;` |
| Property record | `INSERT INTO localization VALUES ('${key}', '${columnName}', '${value}');` |
| Comment record | `-- ${value}` |
| Empty record |  |

There is a line break sign in the end of each pattern.

## Limitations ##
The solution cannot handle properly such [descriptions](FeaturesOfEditor#Overview.md) and [column descriptions](FeaturesOfEditor#Columns.md) which consists of multiple lines. The second and further lines will break the SQL script.

# Implementation #
Follow the instructions in the following sub pages.
  1. [Creating the project](ImplementingNewHandlerProject.md)
  1. [Implementing handler configurator classes](ImplementingNewHandlerConfiguratorClass.md)
  1. [Implementing handler class](ImplementingNewHandlerClass.md)