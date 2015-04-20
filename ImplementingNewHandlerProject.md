Previous step: [How to implement new handler](ImplementingNewHandlerTutorial.md)<br />
Next step: [Implementing handler configurator classes](ImplementingNewHandlerConfiguratorClass.md)


---


# Introduction #
By the end of this wiki page, we will have the skeleton of the new handler.

# Creating the project #
Create a new plug-in project for the new handler. Go to **File** > **New** > **Other...**, then select the **Plug-in Project** under **Plug-in Development** category. The _Project name_ is `hu.skzs.multiproperties.handler.test`. In the following wizard page select the `J2SE-1.5` possibility for the _Execution Environment_. Finally deselect the template selection. We are going to build the plug-in from scratch. Finishing the wizard will create the new project.

## Manifest editor ##
By creating the project the manifest editor will be opened automatically, but you can open it manually anytime by opening the `plugin.xml` file in the root folder of project.

On the **Overview** page of the manifest editor select the **This plug-in is a singleton** checkbox. On the **Dependencies** page add the followings to the **Required Plug-ins** section.
  * `org.eclipse.ui`
  * `org.eclipse.ui.ide`
  * `org.eclipse.core.runtime`
  * `org.eclipse.core.resources`
  * `hu.skzs.multiproperties.base`

![http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/wiki/ImplementingNewHandler/dependencies.jpg](http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/wiki/ImplementingNewHandler/dependencies.jpg)

## Handler classes ##
Create a new Java class in the `hu.skzs.multiproperties.handler.test` package with `TestHandler` name. The class must implement the `hu.skzs.multiproperties.base.api.IHandler` interface.
```java

package hu.skzs.multiproperties.handler.test;

import hu.skzs.multiproperties.base.api.HandlerException;
import hu.skzs.multiproperties.base.api.IHandler;
import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.Table;

public class TestHandler implements IHandler {
public void save(String configuration, Table table, Column column) throws HandlerException {
// TODO Auto-generated method stub
}
}
```

Then create a new Java class in the `hu.skzs.multiproperties.handler.test` package with `TestHandlerConfigurator` name. The class must implement the `hu.skzs.multiproperties.base.api.IHandlerConfigurator` interface.

```java

package hu.skzs.multiproperties.handler.test;

import org.eclipse.swt.widgets.Shell;
import hu.skzs.multiproperties.base.api.HandlerException;
import hu.skzs.multiproperties.base.api.IHandlerConfigurator;

public class TestHandlerConfigurator implements IHandlerConfigurator {
public String configure(Shell shell, String configuration) throws HandlerException {
// TODO Auto-generated method stub
return null;
}
}
```

## Add Handler extension ##
On the **Extensions** page of the manifest editor add a new handler extension. The extension point is `hu.skzs.multiproperties.handler`. The newly created extension parameters are the following:
| **Parameter** | **Value** |
|:--------------|:----------|
| name | `Test Handler` |
| class | `hu.skzs.multiproperties.handler.test.TestHandler` |
| configuratorClass | `hu.skzs.multiproperties.handler.test.TestHandlerConfigurator` |

![http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/wiki/ImplementingNewHandler/extensions.jpg](http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/wiki/ImplementingNewHandler/extensions.jpg)

Alternatively you can also add the extension by editing the `plugin.xml` file in the root of the plug-in project.
```xml
<plugin>
<extension point="hu.skzs.multiproperties.handler">
<handler
class="hu.skzs.multiproperties.handler.test.TestHandler"
configuratorClass="hu.skzs.multiproperties.handler.test.TestHandlerConfigurator"
name="Test Handler">


Unknown end tag for &lt;/handler&gt;




Unknown end tag for &lt;/extension&gt;




Unknown end tag for &lt;/plugin&gt;

```

# Testing #
If you have performed the above steps properly, then on the **Overview** page of manifest editor just click on the **Launch an Eclipse application** link.

![http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/wiki/ImplementingNewHandler/test_multiproperties.jpg](http://svn.codespot.com/a/eclipselabs.org/multiproperties/trunk/hu.skzs.multiproperties.util/wiki/ImplementingNewHandler/test_multiproperties.jpg)


---

Previous step: [How to implement new handler](ImplementingNewHandlerTutorial.md)<br />
Next step: [Implementing handler configurator classes](ImplementingNewHandlerConfiguratorClass.md)