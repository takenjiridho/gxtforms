package com.googlecode.gxtforms.client;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.googlecode.gxtforms.client.config.FieldConfiguration;
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

    protected Field<?> buildField(FieldConfiguration fieldConfig) {
        Field<?> field = initField(fieldConfig);
        field.setFieldLabel(fieldConfig.getLabel());
        field.setName(fieldConfig.getName());
        return field;
    }

    @SuppressWarnings("unchecked")
    protected Field<?> initField(FieldConfiguration fieldConfig) {
        switch (fieldConfig.getType()) {
        case Date:
            return new DateField();
        case Hidden:
            return new HiddenField();
        case Radio:
            return new Radio();
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
                box.setEmptyText("Select a " + fieldConfig.getLabel());
            } else {
                box.setEmptyText(fieldConfig.getEmptyText());
            }

            List<? extends FieldOption<?>> options = fieldConfig.getOptions();
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
                }
            }
            box.setStore(store);
            return box;
        case Text:
            return new TextField();
        case TextArea:
            return new TextArea();
        default:
            throw new FieldConfigurationException("unmapped field type: " + fieldConfig.getType());
        }

    }

}
