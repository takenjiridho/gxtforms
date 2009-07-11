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
@FormField()
public @interface CheckBoxField {

    /**
     * FieldType that will be rendered.
     */
    FieldType fieldType() default FieldType.Checkbox;

    /**
     * Displayed to users.
     */
    String label() default "";

    /**
     * Underlying form field name.
     */
    String name() default "";

    /**
     * The fields order with respect to other fields.
     */
    int order() default 0;

    /**
     * Whether or not a field is required.
     */
    boolean required() default false;

}
