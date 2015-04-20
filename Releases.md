# 1.3.1 #
2014-03-24 [r269](https://code.google.com/p/multiproperties/source/detail?r=269)
  * [issue25](https://code.google.com/p/multiproperties/issues/detail?id=25) "multiline property value with about 500kb text" related to Multi line value support


# 1.3.0 #
2014-02-16 [r266](https://code.google.com/p/multiproperties/source/detail?r=266)
  * [issue25](https://code.google.com/p/multiproperties/issues/detail?id=25) Multi line value support is missing
  * [issue29](https://code.google.com/p/multiproperties/issues/detail?id=29) Changes for columns properties or descriptions not possible to save!
  * [issue34](https://code.google.com/p/multiproperties/issues/detail?id=34) Moving up/down records can run out of the visible area

# 1.2.0 #
2012-11-14 [r238](https://code.google.com/p/multiproperties/source/detail?r=238)
  * [issue26](https://code.google.com/p/multiproperties/issues/detail?id=26) [Text File handler](TextFileHandler.md)
  * [issue27](https://code.google.com/p/multiproperties/issues/detail?id=27) Unable to switch off the Properties Handler for a particular column
  * [issue23](https://code.google.com/p/multiproperties/issues/detail?id=23) Improved facade for [command-line execution](CommandLineHandlerExecutor.md)
  * [issue20](https://code.google.com/p/multiproperties/issues/detail?id=20) No icon is visible for the Java Importer selection

# 1.1.0 #
2012-09-13 [r117](https://code.google.com/p/multiproperties/source/detail?r=117)
  * [issue7](https://code.google.com/p/multiproperties/issues/detail?id=7) [Import](ImportingContent.md) properties files
  * [issue19](https://code.google.com/p/multiproperties/issues/detail?id=19) IllegalArgumentException is thrown in some cases
  * [issue17](https://code.google.com/p/multiproperties/issues/detail?id=17) On Windows XP the widgets does not have visible borders on the Overview and Columns page. Furthermore the handler selector combo is not flat.
  * [issue16](https://code.google.com/p/multiproperties/issues/detail?id=16) The command line usage requires too much class dependency
  * [issue18](https://code.google.com/p/multiproperties/issues/detail?id=18) Exception is thrown when the Java handler configuration wizard is canceled
# 1.0.0 #
2012-05-23 [r108](https://code.google.com/p/multiproperties/source/detail?r=108)
  * [issue11](https://code.google.com/p/multiproperties/issues/detail?id=11) Supporting default value
  * [issue12](https://code.google.com/p/multiproperties/issues/detail?id=12) [Command line execution](CommandLineHandlerExecutor.md)
  * [issue13](https://code.google.com/p/multiproperties/issues/detail?id=13) Changing the activation of inline editing
  * [issue6](https://code.google.com/p/multiproperties/issues/detail?id=6) Outline view is not refreshed after editing a property typed record
## Note ##
When you try to open a previous MultiProperties file with the new 1.0.0 version, you will be prompted for updating the file to the new format.
# 0.9.1 #
2012-02-27 [r82](https://code.google.com/p/multiproperties/source/detail?r=82)
  * [issue8](https://code.google.com/p/multiproperties/issues/detail?id=8) Description of multiproperties file should be used as comment at top of all peroperties files
  * [issue3](https://code.google.com/p/multiproperties/issues/detail?id=3) Java Properties Handler doesn't handle the disabled record well
  * [issue2](https://code.google.com/p/multiproperties/issues/detail?id=2) The new MPE wizard doesn't allow to specify the description
  * [issue4](https://code.google.com/p/multiproperties/issues/detail?id=4) The plugin doesn't log into the framework's log file
  * [issue9](https://code.google.com/p/multiproperties/issues/detail?id=9) Localization support is missing
  * [issue10](https://code.google.com/p/multiproperties/issues/detail?id=10) Supporting different XML file format
## Note ##
The previous [0.9.0](#0.9.0.md) version used invalid MultiProperties file format version value. Such a `.multiproperties` should be modified manually before trying to open it with the [0.9.1](#0.9.1.md) version. Ensure that the `.multiproperties` file has `1.0` value in the `Version` element in the beginning part of the XML instead of `2.0`.
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<MultiProperties xmlns="hu.skzs.multiproperties">
  <Version>1.0</Version>
  ...
</MultiProperties>
```
# 0.9.0 #
2011-11-24 [r45](https://code.google.com/p/multiproperties/source/detail?r=45)
  * First version