# MultiProperties Editor

MultiProperties Editor is an [Eclipse](http://eclipse.org/) plugin for editing multiple key-value based files with similar content. This kind of file format can be [Properties](http://docs.oracle.com/javase/tutorial/essential/environment/properties.html) for example in Java programming language, which is frequently used for [backing a ResourceBundle](http://docs.oracle.com/javase/tutorial/i18n/resbundle/propfile.html). 

However, the plugin is not limited to support only [Properties](http://docs.oracle.com/javase/tutorial/essential/environment/properties.html) files. Instead, it stores everything in one XML file, including the list of keys, all of the values, descriptions and meta information. When the editor saves its content into the XML file, it also saves different a kind of output files with help of handlers. 

![Concept of the MultiProperties](https://raw.githubusercontent.com/skazsi/multiproperties/master/hu.skzs.multiproperties.util/image/mpe_concept.gif "Concept of the MultiProperties")

## Download
You can find the MultiProperties on the [Eclipse Marketplace](http://marketplace.eclipse.org/content/multiproperties), however if you wish to install it manually use the following update site.

`https://raw.githubusercontent.com/skazsi/multiproperties/master/hu.skzs.multiproperties.updatesite`

If you want to produce Properties output files, install MultiProperties Java Handler feature too in addition of core MultiProperties feature. 


