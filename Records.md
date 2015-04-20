# Record types #

The MultiProperties Editor currently supports three different record types. They are generic terms, because the editor itself cannot know how the configured handler will use them during producing some kind ouf output.
  * ![http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.ui/icons/record.gif](http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.ui/icons/record.gif) **Property**
    * The **Property** record has a **Multiline** subtype which is decorated with a ![http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.ui/icons/line-break.png](http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.ui/icons/line-break.png) icon
  * ![http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.ui/icons/comment.gif](http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.ui/icons/comment.gif) **Comment**
  * ![http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.ui/icons/empty.gif](http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.ui/icons/empty.gif) **Empty**

## Property ##
This is the most important record type. It has at first one key and as many values as many columns exists in the MultiProperties file. The _undefined_ is also a possible value, which means the key is not defined in the current column. In the latter case the default value can provide a value if it used for the property record.

The property record has also a disabled flag and a description value.

![http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/wiki/Records/Records01.jpg](http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/wiki/Records/Records01.jpg)

While the above seen picture represents a normal property edit dialog, until the below pictures represent a multiline property edit dialog. Note the navigation tree on the left side. Especially in a multiline property it makes easily the navigation between the pages.

![http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/wiki/Records/Records_multiline_01.jpg](http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/wiki/Records/Records_multiline_01.jpg)

![http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/wiki/Records/Records_multiline_02.jpg](http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/wiki/Records/Records_multiline_02.jpg)


## Comment ##
The comment record is useful for inserting comments between the properties. Although it is up to the configured handler how it will be written out into the output files, but in case of **Java Properties Handler** these records will be written with `#` prefix character.

## Empty ##
The empty record is used only for cosmetic purpose. In case of **Java Properties Handler** these records will result only empty lines.