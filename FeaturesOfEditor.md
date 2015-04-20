# MultiProperties Editor #
The MultiProperties Editor is a multi-page editor with three pages as follows.
  * ![http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.ui/icons/overview.gif](http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.ui/icons/overview.gif) **Overview** - general information about the MultiProperties file, and the handler selection is possible here.
  * ![http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.ui/icons/columns.gif](http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.ui/icons/columns.gif) **Columns** - editing columns. Handler configuration for each particular column is also possible here.
  * ![http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.ui/icons/table.gif](http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.ui/icons/table.gif) **Table** - editing MultiProperties. It is basically a table where the first column is the key column, then each following column is a value column configured on the **Columns** page.

## Overview ##
This page has four different sections. In the **General Information** you can specify the **Name** and **Description** values of the current MultiProperties file. These values are not used for other functions, so you can enter any kind of values here according to you needs.

![http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/wiki/FeaturesOfEditor/overview.jpg](http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/wiki/FeaturesOfEditor/overview.jpg)

In the **Handler** section you can select an installed handler to be used for the current MultiProperties file. Note that you cannot select more than one, which means if you select for example the **Java Properties Handler**, then each columns can be saved into [Properties](http://docs.oracle.com/javase/tutorial/essential/environment/properties.html) files only. You cannot mix different handlers in one MultiProperties file.

If you want to change the already used handler to a different one, you have to confirm the change by a dialog window, because each columns will lose its handler configuration.

## Columns ##
On this page you can edit the columns of the editor. You can **Add**, **Remove**, **Move up** or **Move down** columns. The [content importing](ImportingContent.md) can be triggered also from here.

![http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/wiki/FeaturesOfEditor/columns.jpg](http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/wiki/FeaturesOfEditor/columns.jpg)

After you have selected a column, then you can edit its **Name** and **Description** properties. Furthermore if you have selected a handler, you can configure the handler settings for this columns by pressing the **Configure...** button. In case of **Java Properties Handler**, it means you can specify the target workspace folder and the desired file name for the [Properties](http://docs.oracle.com/javase/tutorial/essential/environment/properties.html) file in addition of further settings.

## Table ##
This page offers basically the editing capabilities of the content. You can add new [property, comment or empty](Records.md) typed records, but you can also delete, edit and move the selected records in addition of some special features, like duplicating and disabling.

![http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/wiki/FeaturesOfEditor/table.jpg](http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/wiki/FeaturesOfEditor/table.jpg)

Although the edit wizard window offers quite comfortable editing, you can also edit a cell value directly by **Alt + mouse click** combination.