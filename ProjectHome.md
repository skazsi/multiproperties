## About the MultiProperties ##
**MultiProperties Editor** is an [Eclipse](http://eclipse.org/) plugin for editing multiple key-value based files with similar content. This kind of file format can be [Properties](http://docs.oracle.com/javase/tutorial/essential/environment/properties.html) for example in Java programming language, which is frequently used for [backing a ResourceBundle](http://docs.oracle.com/javase/tutorial/i18n/resbundle/propfile.html).

However the plugin is not limited to support only [Properties](http://docs.oracle.com/javase/tutorial/essential/environment/properties.html) files. Instead it stores everything in one XML file, including the list of keys, all of the values, descriptions and meta information. When the editor saves its content into the XML file, it also saves different kind of output files with help of handlers.

![http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/image/mpe_concept.gif](http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/image/mpe_concept.gif)

Since the handlers are used through standard [Eclipse](http://eclipse.org/) extension point, not just the already available handlers can be used. [You can also implement your own handler](ImplementingNewHandlerTutorial.md) which produces your own output format, for example you can produce SQL files instead of [Properties](http://docs.oracle.com/javase/tutorial/essential/environment/properties.html) files.

More about the [Available Handlers and Importers](HandlersImporters.md).

<a href='https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=HYLFXQL54MR8W'>
<img src='https://www.paypalobjects.com/en_US/i/btn/btn_donate_SM.gif' border='0' />
</a>

## Screenshots ##
<table cellpadding='0' cellspacing='0' border='0'><tr><td width='488px' height='319px'>
<a href='http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/image/screenshot1.jpg'><img src='http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/image/screenshot1.jpg' width='488px' height='319px' /></a></td><td width='488px' height='319px'>
<a href='http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/image/screenshot2.jpg'><img src='http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/image/screenshot2.jpg' width='488px' height='319px' /></a></td></tr></table>

## Latest releases ##
See all [previous releases](Releases.md).
### 1.3.1 ###
2014-03-24 [r269](https://code.google.com/p/multiproperties/source/detail?r=269)
  * [issue25](https://code.google.com/p/multiproperties/issues/detail?id=25) "multiline property value with about 500kb text" related to Multi line value support
### 1.3.0 ###
2014-02-16 [r266](https://code.google.com/p/multiproperties/source/detail?r=266)
  * [issue25](https://code.google.com/p/multiproperties/issues/detail?id=25) Multi line value support is missing
  * [issue29](https://code.google.com/p/multiproperties/issues/detail?id=29) Changes for columns properties or descriptions not possible to save!
  * [issue34](https://code.google.com/p/multiproperties/issues/detail?id=34) Moving up/down records can run out of the visible area
## Download ##
You can download and install the plugin with the [Eclipse](http://eclipse.org/) update manager from the following address.

`http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.updatesite/`

If you want to produce [Properties](http://docs.oracle.com/javase/tutorial/essential/environment/properties.html) output files, install **MultiProperties Java Handler** feature too in addition of core **MultiProperties** feature.
