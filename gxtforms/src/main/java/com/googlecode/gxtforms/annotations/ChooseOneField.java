package com.googlecode.gxtforms.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.googlecode.gxtforms.client.config.FieldType;
import com.googlecode.gxtforms.client.config.NullEnum;

@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@FormField()
public @interface ChooseOneField {
    
    /**
     * FieldType that will be rendered.
     */
    FieldType fieldType() default FieldType.SelectOne;
    
    /**
     * Displayed to users.
     */
    String label() default "";

    /**
     * The fields order with respect to other fields.
     */
    int order() default 0;
    
    /**
     * Underlying form field name.
     */
    String name() default "";

    /**
     * Whether or not a field is required.
     */
    boolean required() default false;
    
    /**
     * Value displayed to a user when there is no value in a field.
     */
    String emptyText() default "";
    
    /**
     * Enumeration of values to be used as values in choose one. 
     */
    Class<? extends Enum<?>> enumClass() default NullEnum.class;

}
