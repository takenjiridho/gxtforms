package com.googlecode.gxtforms.client;

import java.util.List;

import com.extjs.gxt.ui.client.binding.Converter;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.googlecode.gxtforms.client.config.FieldConfiguration;
import com.googlecode.gxtforms.client.config.FieldType;

public class EnumConverter extends Converter {
    
    private FieldConfiguration fieldConfig;
    
    public EnumConverter(FieldConfiguration fieldConfig) {
        this.fieldConfig = fieldConfig;
    }
    
    @Override
    public Object convertFieldValue(Object value) {
        if (value == null) return null;
        if (fieldConfig.getFieldType() == FieldType.SelectOne) {
            return convertFromModelData(value);
        } else {
            return convertFromRadio(value);
        }
    }
    
    @SuppressWarnings("unchecked")
    private Object convertFromRadio(Object value) {
        String label = ((Radio) value).getBoxLabel();
        List<EnumFieldOption> options = (List<EnumFieldOption>) fieldConfig.getOptions();
        for (EnumFieldOption option : options) {
            if (option.getLabel().equals(label)) {
                return option.getValue();
            }
        }
        return null;
    }

    private Object convertFromModelData(Object value) {
        ModelData model = (ModelData) value;
        Enum<?> enumVal = model.get("value");
        return enumVal;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object convertModelValue(Object value) {
        if (value == null) return null;
        List<EnumFieldOption> options = (List<EnumFieldOption>) fieldConfig.getOptions();
        for (EnumFieldOption option : options) {
            if (option.getValue() == value) {
                if (fieldConfig.getFieldType() == FieldType.Radio) {
                    return buildRadio(option.getLabel());
                } else {
                    return buildModelData(value, option);
                }
            }
        }
        return null;
    }

    private Radio buildRadio(String label) {
        Radio radio = new Radio();
        radio.setBoxLabel(label);
        return radio;
    }
    
    private ModelData buildModelData(Object value, EnumFieldOption option) {
        ModelData model = new BaseModelData();
        model.set("value", value);
        model.set("label", option.getLabel());
        return model;
    }

}
