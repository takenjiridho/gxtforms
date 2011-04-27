package com.googlecode.gxtforms.client.form;

import java.util.List;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.BindingEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Layout;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.googlecode.gxtforms.client.config.FieldConfiguration;
import com.googlecode.gxtforms.client.config.FieldType;
import com.googlecode.gxtforms.client.config.FormConfiguration;
import com.googlecode.gxtforms.client.config.RegexWithMessage;
import com.googlecode.gxtforms.client.converters.EnumConverter;
import com.googlecode.gxtforms.client.exceptions.FieldConfigurationException;
import com.googlecode.gxtforms.client.field.EnumFieldOption;
import com.googlecode.gxtforms.client.field.FieldOption;
import com.googlecode.gxtforms.client.utils.CollectionUtils;
import com.googlecode.gxtforms.client.utils.StringUtils;

public abstract class GXTFormBuilder<T extends FormPanel> implements FormBuilder<T> {

    public static final int DEFAULT_LABEL_WIDTH = 150;


    public GXTFormBuilder() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.googlecode.gxtforms.client.form.FormBuilder#buildFormPanel(com.googlecode
     * .gxtforms.client.config.FormConfiguration,
     * com.extjs.gxt.ui.client.widget.Layout)
     */
    public T buildFormPanel(FormConfiguration formConfig, Layout layout) {
        T panel = initFormPanel(formConfig.getFormPanelConfiguration());
        panel.setLayout(layout);

        FieldSet fieldSet = null;
        String lastFieldSetTitle = null;
        for (FieldConfiguration fieldConfig : formConfig.getFieldConfigurations()) {
            Field<?> field = buildField(fieldConfig);
            String fieldSetTitle = fieldConfig.getFieldSet();

            if (fieldSet != null) {
                if (StringUtils.isEmpty(fieldSetTitle) || fieldSetTitle.equals(lastFieldSetTitle)) {
                    // add to existing field set
                    fieldSet.add(field);
                } else if (StringUtils.isNotEmpty(fieldSetTitle)) {
                    // new field set
                    fieldSet = newFieldSet(panel, field, fieldSetTitle, formConfig);
                    lastFieldSetTitle = fieldSetTitle;
                }
            } else if (StringUtils.isNotEmpty(fieldSetTitle)) {
                fieldSet = newFieldSet(panel, field, fieldSetTitle, formConfig);
                lastFieldSetTitle = fieldSetTitle;
            } else {
                // not part of a field set
                panel.add(field);
            }

        }
        return panel;
    }

    protected FieldSet newFieldSet(FormPanel panel, Field<?> field, String fieldSetTitle, FormConfiguration formConfig) {
        FieldSet fieldSet;
        fieldSet = new FieldSet();
        fieldSet.setWidth("95%");
        FormLayout layout = new FormLayout();
        FormPanelConfiguration formPanelConfiguration = formConfig.getFormPanelConfiguration();
        layout.setLabelAlign(LabelAlign.valueOf(formPanelConfiguration.getLabelAlign().name()));
        int labelWidth = formPanelConfiguration.getLabelWidth();
        setLabelWidth(layout, labelWidth);
        fieldSet.setLayout(layout);
        fieldSet.setHeading(fieldSetTitle);
        fieldSet.add(field);
        panel.add(fieldSet);
        return fieldSet;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.googlecode.gxtforms.client.form.FormBuilder#bind(com.googlecode.gxtforms
     * .client.config.FormConfiguration,
     * com.extjs.gxt.ui.client.widget.form.FormPanel, java.lang.Object)
     */
    public void bind(FormConfiguration formConfig, FormPanel formPanel, Object bean) {
        final FormBinding binding = new FormBinding(formPanel, true);
        if (bean instanceof ModelData) {
            binding.bind((ModelData) bean);
        } else {
            BeanModelFactory factory = BeanModelLookup.get().getFactory(bean.getClass());
            BeanModel model = factory.createModel(bean);
            binding.bind(model);
        }

        for (FieldBinding fieldBinding : binding.getBindings()) {
            Field<Object> field = fieldBinding.getField();
            FieldConfiguration fieldConfig = formConfig.getFieldConfiguration(field.getName());
            if (isEnumField(fieldConfig)) {
                fieldBinding.setConverter(new EnumConverter(fieldConfig));
            }
        }

        // clear validation errors -- don't want to see those on initial render
        binding.addListener(Events.Bind, new Listener<BindingEvent>() {
            public void handleEvent(BindingEvent be) {
                for (FieldBinding fieldBinding : binding.getBindings()) {
                    fieldBinding.getField().clearInvalid();
                }
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.googlecode.gxtforms.client.form.FormBuilder#buildFormPanel(com.googlecode
     * .gxtforms.client.config.FormConfiguration, java.lang.Object)
     */
    public T buildFormPanel(FormConfiguration formConfig, Object bean) {
        T panel = buildFormPanel(formConfig);
        bind(formConfig, panel, bean);
        return panel;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.googlecode.gxtforms.client.form.FormBuilder#buildFormPanel(com.googlecode
     * .gxtforms.client.config.FormConfiguration)
     */
    public T buildFormPanel(FormConfiguration formConfig) {
        FormLayout layout = new FormLayout();
        FormPanelConfiguration formPanelConfiguration = formConfig.getFormPanelConfiguration();
        layout.setLabelAlign(LabelAlign.valueOf(formPanelConfiguration.getLabelAlign().name()));
        int labelWidth = formPanelConfiguration.getLabelWidth();
        setLabelWidth(layout, labelWidth);
        return buildFormPanel(formConfig, layout);
    }

    private void setLabelWidth(FormLayout layout, int labelWidth) {
        if (labelWidth > 0) {
            layout.setLabelWidth(labelWidth);
        } else {
            layout.setLabelWidth(DEFAULT_LABEL_WIDTH);
        }
    }

    protected T initFormPanel(FormPanelConfiguration config) {
        T panel = constructPanel();

        panel.setFrame(config.isFrame());
        panel.setAnimCollapse(config.isAnimCollapse());
        panel.setCollapsible(config.isCollapsible());
        panel.setHideLabels(config.isHideLabels());

        String action = config.getAction();
        if (StringUtils.isNotEmpty(action)) {
            panel.setAction(config.getAction());
        }

        int fieldWidth = config.getFieldWidth();
        if (fieldWidth > 0) {
            panel.setFieldWidth(fieldWidth);
        }

        int width = config.getWidth();
        if (width > 0) {
            panel.setWidth(width);
        }

        String method = config.getMethod();
        if (StringUtils.isNotEmpty(method)) {
            panel.setMethod(FormPanel.Method.valueOf(method));
        }

        String heading = config.getHeading();
        if (StringUtils.isNotEmpty(heading)) {
            panel.setHeading(heading);
        }

        return panel;
    }

    @SuppressWarnings("rawtypes")
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
                final RegexWithMessage validator = fieldConfig.getValidator();
                if (validator != null && StringUtils.isNotEmpty(validator.getRegex())) {
                    textField.setValidator(new Validator() {
                        public String validate(Field<?> field, String value) {
                            if (!value.matches(validator.getRegex())) {
                                return validator.getMessage();
                            } else {
                                return null;
                            }
                        }
                    });
                }
            }

            if (field instanceof RadioGroup) {
                RadioGroup radio = (RadioGroup) field;
                radio.setOrientation(Orientation.valueOf(fieldConfig.getOrientation().name()));
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

            String styleName = fieldConfig.getStyleName();
            if (StringUtils.isNotEmpty(styleName)) {
                field.addStyleName(styleName);
            }

            field.setEnabled(fieldConfig.isEnabled());
        }

        return field;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected Field<?> initField(FieldConfiguration fieldConfig) {
        List<? extends FieldOption<?>> options = fieldConfig.getOptions();
        switch (fieldConfig.getFieldType()) {
        case Date:
            return new DateField();
        case Hidden:
            return new HiddenField();
        case Radio:
            RadioGroup radio = new RadioGroup();
            if (isEnumField(fieldConfig)) {
                for (EnumFieldOption option : (List<EnumFieldOption>) options) {
                    Radio radioOption = new Radio();
                    radioOption.setBoxLabel(option.getLabel());
                    radio.add(radioOption);
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
            if (isEnumField(fieldConfig)) {
                for (EnumFieldOption option : (List<EnumFieldOption>) options) {
                    ModelData model = new BaseModelData();
                    model.set("value", option.getValue());
                    model.set("label", option.getLabel());
                    store.add(model);
                }
                box.setDisplayField("label");
                box.setValueField("value");
                box.setTriggerAction(TriggerAction.ALL);
                box.setForceSelection(true);
            }
            box.setStore(store);
            return box;
        case Text:
            return new TextField();
        case TextArea:
            return new TextArea();
        default:
            throw new FieldConfigurationException("Unmapped field type: " + fieldConfig.getFieldType());
        }

    }

    protected boolean isEnumField(FieldConfiguration fieldConfig) {
        List<? extends FieldOption<?>> options = fieldConfig.getOptions();
        if ((fieldConfig.getFieldType() == FieldType.SelectOne || fieldConfig.getFieldType() == FieldType.Radio)
                && CollectionUtils.isNotEmpty(options) && options.get(0) instanceof EnumFieldOption) {
            return true;
        } else {
            return false;
        }
    }
}
