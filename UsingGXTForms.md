## Usage Notes ##

You might want to have a look at the source code for the demo here:

> http://code.google.com/p/gxtforms/source/browse/trunk/gxtforms-demo/

The demo application has client source and annotations tabs at the bottom for an example:

> http://gxtforms.appspot.com/

There are a few things the app service demo doesn't illustrate (but can be seen in the demo source code).  Here's what the demo app's FormService declaration looks like:

```
    <servlet>
        <servlet-name>FormService</servlet-name>
        <servlet-class>com.googlecode.gxtforms.server.FormServiceImpl</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>FormService</servlet-name>
        <url-pattern>/com.googlecode.gxtforms.demo.Application/FormService</url-pattern>
    </servlet-mapping>
```

And the gwt module file has the following.

```
   <inherits name='com.googlecode.gxtforms.GXTForms'/> 
```

The maven pom.xml has this:

```

        <dependency>
            <groupId>com.googlecode.gxtforms</groupId>
            <artifactId>gxtforms</artifactId>
            <version>0.1</version>
        </dependency>

        <dependency>
            <groupId>com.extjs</groupId>
            <artifactId>gxt</artifactId>
            <version>2.0.1</version>
        </dependency>

```




## Running the demo locally ##

If you're so inclined to run the demo application locally, check out the source from svn and execute:

> mvn gwt:run

from the root of the demo source.  You'll need the gxtforms library installed to your local repo first.