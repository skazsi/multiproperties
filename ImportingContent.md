# Importing content #
You can import content into your MultiProperties file by triggering the **Import** function. Go to the ![http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.ui/icons/columns.gif](http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.ui/icons/columns.gif) [Columns](FeaturesOfEditor#Columns.md) page and click on the **Import** button.

On the appearing wizard you need to select one of the possible importers.

![http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/wiki/Import/Import01.jpg](http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/wiki/Import/Import01.jpg)

Please note that the **Importers** need to be implemented differently under different Eclipse extension point. It means not every **Handler** plugin offers **Importer** possibilities. But for sure the **Java Properties Handler** includes this feature.

On the following page you need to select the method to be used.

![http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/wiki/Import/Import02.jpg](http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/wiki/Import/Import02.jpg)

Please note that the **Key-value based import** and **Value import** methods are not available if no column is selected on the ![http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.ui/icons/columns.gif](http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.ui/icons/columns.gif) [Columns](FeaturesOfEditor#Columns.md) page before clicking on the **Import** button.
  * **Structure import** - Importing the structure includes not just the property records, but the comment and empty records too. All imported record will be appended in the end of the table. The property values are not imported.
  * **Key-value based import** - Importing only the property records together with their values. If the property already exists, then only its value is overwritten. Otherwise a new property will be inserted in the end of the table.
  * **Value import** - Importing only values of the already existing properties. If the property does not exist, it will not be created.

On the following pages the selected importer's settings are represented.

## Java Properties importer ##

Specify the properties file to be imported then click on the **Finish** button.

![http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/wiki/Import/Import03.jpg](http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/wiki/Import/Import03.jpg)

After the importing on the ![http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.ui/icons/table.gif](http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.ui/icons/table.gif) [Table](FeaturesOfEditor#Table.md) page you can see the imported content immediately.