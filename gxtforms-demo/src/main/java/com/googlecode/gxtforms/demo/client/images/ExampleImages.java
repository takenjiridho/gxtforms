package com.googlecode.gxtforms.demo.client.images;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

public interface ExampleImages extends ImageBundle {

    public static ExampleImages BUNDLE = GWT.create(ExampleImages.class);
    
    AbstractImagePrototype simpleForm();
    
    AbstractImagePrototype indexedForm();
    
    AbstractImagePrototype customStylesForm();

    AbstractImagePrototype fieldSetForm();

    AbstractImagePrototype nestedBeansForm();
    
    
}
