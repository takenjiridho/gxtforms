package com.googlecode.gxtforms.client.form;

import com.extjs.gxt.ui.client.widget.Layout;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.googlecode.gxtforms.client.config.FormConfiguration;

public interface FormBuilder {

    FormPanel buildFormPanel(FormConfiguration formConfig, Layout layout);

    void bind(FormConfiguration formConfig, FormPanel formPanel, Object bean);

    FormPanel buildFormPanel(FormConfiguration formConfig, Object bean);

    FormPanel buildFormPanel(FormConfiguration formConfig);

}