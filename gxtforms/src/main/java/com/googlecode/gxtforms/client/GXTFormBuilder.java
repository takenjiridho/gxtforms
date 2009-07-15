package com.googlecode.gxtforms.client;

import java.util.List;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Layout;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.googlecode.gxtforms.client.config.FieldConfiguration;
import com.googlecode.gxtforms.client.config.FieldType;
import com.googlecode.gxtforms.client.config.FormConfiguration;
import com.googlecode.gxtforms.client.utils.CollectionUtils;
import com.googlecode.gxtforms.client.utils.StringUtils;

public class GXTFormBuilder {

    public GXTFormBuilder() {
        super();
    }

    public FormPanel buildFormPanel(FormConfiguration formConfig, Layout layout) {
        FormPanel panel = new FormPanel();
        panel.setLayout(layout);
        for (FieldConfiguration fieldConfig : formConfig.getFieldConfigurations()) {
            panel.add(buildField(fieldConfig));
        }
        return panel;
    }

    public FormPanel buildFormPanel(FormConfiguration formConfig) {
        FormLayout layout = new FormLayout();
        layout.setLabelAlign(LabelAlign.LEFT);
        layout.setLabelWidth(150);
        return buildFormPanel(formConfig, layout);
    }

    @SuppressWarnings("unchecked")
    protected Field<?> buildField(FieldConfiguration fieldConfig) {
        Field<?> field = initField(fieldConfig);
        field.setFieldLabel(fieldConfig.getFieldLabel());
        field.setName(fieldConfig.getName());
        
        if (fieldConfig.getFieldType() != FieldType.Hidden) {
            field.setAutoValidate(fieldConfig.isAutoValidate());
            field.setHideLabel(fieldConfig.isHideLabel());
            
            if (field instanceof TextField) {
                TextField textField = (TextField) field;
                textField.setAllowBlank(fieldConfig.isAllowBlank());
                int maxLength = fieldConfig.getMaxLength();
                if (maxLength > 0) {
                    textField.setMaxLength(maxLength);
                }
            }
            
            if (field instanceof RadioGroup) {
                RadioGroup radio = (RadioGroup) field;
                Orientation orientation = Orientation.HORIZONTAL;
                if (fieldConfig.getOrientation() == com.googlecode.gxtforms.client.config.Orientation.Vertical) {
                    orientation = Orientation.VERTICAL;
                }
                radio.setOrientation(orientation);
            }

            String labelSeparator = fieldConfig.getLabelSeparator();
            if (StringUtils.isNotEmpty(labelSeparator)) {
                field.setLabelSeparator(labelSeparator);
            }
            
            String labelStyle = fieldConfig.getLabelStyle();
            if (StringUtils.isNotEmpty(labelStyle)) {
                field.setLabelStyle(labelStyle);
            }
            
            field.setReadOnly(fieldConfig.isReadOnly());
            field.setValidateOnBlur(fieldConfig.isValidateOnBlur());
    
            int validationDelay = field.getValidationDelay();
            if (validationDelay > 0) {
                field.setValidationDelay(validationDelay);
            }
            
            String emptyText = fieldConfig.getEmptyText();
            if (StringUtils.isNotEmpty(emptyText)) {
                field.setEmptyText(emptyText);
            }
            
            String messageTarget = fieldConfig.getMessageTarget();
            if (StringUtils.isNotEmpty(messageTarget)) {
                field.setMessageTarget(messageTarget);
            }
        }
        
        return field;
    }

    @SuppressWarnings("unchecked")
    protected Field<?> initField(FieldConfiguration fieldConfig) {
        List<? extends FieldOption<?>> options = fieldConfig.getOptions();
        switch (fieldConfig.getFieldType()) {
        case Date:
            return new DateField();
        case Hidden:
            return new HiddenField();
        case Radio:
            RadioGroup radio = new RadioGroup();
            if (CollectionUtils.isNotEmpty(options)) {
                if (options.get(0) instanceof EnumFieldOption) {
                    for (EnumFieldOption option : (List<EnumFieldOption>) options) {
                        Radio radioOption = new Radio();
                        radioOption.setBoxLabel(option.getLabel());
                        radio.add(radioOption);
                    }
                }
            }
            return radio;
        case Password:
            TextField field = new TextField();
            field.setPassword(true);
            return field;
        case Checkbox:
            return new CheckBox();
        case SelectMany:
            // TODO: implement
            return null;
        case SelectOne:
            ComboBox box = new ComboBox();
            if (StringUtils.isEmpty(fieldConfig.getEmptyText())) {
                box.setEmptyText("Select a " + fieldConfig.getFieldLabel());
            } else {
                box.setEmptyText(fieldConfig.getEmptyText());
            }

            ListStore store = new ListStore();
            if (CollectionUtils.isNotEmpty(options)) {
                if (options.get(0) instanceof EnumFieldOption) {
                    for (EnumFieldOption option : (List<EnumFieldOption>) options) {
                        ModelData model = new BaseModelData();
                        model.set("value", option.getValue());
                        model.set("label", option.getLabel());
                        store.add(model);
                    }
                    box.setDisplayField("label");
                    box.setValueField("value");
                    box.setTriggerAction(TriggerAction.ALL);
                }
            }
            box.setStore(store);
            return box;
        case Text:
            return new TextField();
        case TextArea:
            return new TextArea();
        default:
            throw new FieldConfigurationException("unmapped field type: " + fieldConfig.getFieldType());
        }

    }

}
