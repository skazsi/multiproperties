# Encoding fork for Multiproperties Editor

This is a fork for MultiProperties Editor, please see [the original doc](https://github.com/skazsi/multiproperties)

This fork was mainly intended to add UTF-8 encoding capability for Java properties. It also allows other encoding, but they are not fully tested.  

## Download
 A merge request will be done in order to include this into the original update site, however if you wish to install it manually use the following one

`https://raw.githubusercontent.com/tazurc/multiproperties/master/hu.skzs.multiproperties.updatesite`

Look for the Multiproperties Java Handler Feature version 1.3.0.utf8

## How to use

#### Create Multiproperties file
* Ctrl+N > Multiproperties 
* Enter location, file name and name > Finish
* Overview tab > Handler > Java Properties Handler
* Columns tab > Add... >  Name (you should have one column per locale you want to support)
* Select a column and click the Configure... button 
* (optional) Check Enable output properties file writing, enter location and file name, this will export your entries to a Java properties file
* Select a different encoding if applies
* Do this for each column if needed
* Save and check if your java properties files are created

#### Import from existing Java properties

Important: the existing Java properties should be encoded with the same as the column specific configuration. If not, first change the encoding of the column, import and then come back to the desired encoding output for the column   

* Open your multiproperties file > Column tab
* Select a Column > Import... > Java Properties Importer > Next
* Key-value-base import > Next
* Enter file > Finish
* Do this for each column if needed
* Save

#### Editing Java properties

* Open your multiproperties file > Table tab
* Double-click the desired line to edit for a specific configuration or edition
* For a quick inline edition, on the toolbar there's a Toggle Inline Edit mode 
* Save to update your properties file


