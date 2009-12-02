package com.googlecode.gxtforms.client.field;

@SuppressWarnings("serial")
public class EnumFieldOption extends FieldOption<Enum<?>> {

    public EnumFieldOption() {
        super();
    }

    public EnumFieldOption(Enum<?> value, String label) {
        super(value, label);
    }

}
