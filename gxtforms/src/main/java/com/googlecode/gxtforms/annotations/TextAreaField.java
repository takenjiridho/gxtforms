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
public @interface TextAreaField {

    /**
     * FieldType that will be rendered.
     */
    FieldType fieldType() default FieldType.TextArea;

    /**
     * Displayed to users.
     */
    String fieldLabel() default "";

    /**
     * Underlying form field name.
     */
    String name() default "";

    /**
     * The fields order with respect to other fields.
     */
    int index() default 0;

    /**
     * Whether or not a field is required.
     */
    boolean allowBlank() default true;

    /**
     * Value displayed to a user when there is no value in a field.
     */
    String emptyText() default "";
    
    boolean autoValidate() default true;
    
    String labelSeparator() default ":"; 

    String labelStyle() default "";
    
    String messageTarget() default "";
    
    boolean validateOnBlur() default false;
    
    int validationDelay() default 200;
    
    boolean hideLabel() default false;
    
    boolean readOnly() default false;    
    
    int maxLength() default 0;

}
