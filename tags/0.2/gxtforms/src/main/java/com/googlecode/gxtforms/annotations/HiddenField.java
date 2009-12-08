package com.googlecode.gxtforms.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.googlecode.gxtforms.client.config.FieldType;

@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@FormAnnotation()
public @interface HiddenField {

    /**
     * FieldType that will be rendered.
     */
    FieldType fieldType() default FieldType.Hidden;

    /**
     * Underlying form field name.
     */
    String name() default "";

    int index() default 0;

    String fieldSet() default "";

}
