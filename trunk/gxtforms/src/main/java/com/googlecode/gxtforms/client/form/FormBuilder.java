package com.googlecode.gxtforms.client.form;

import com.extjs.gxt.ui.client.widget.Layout;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.googlecode.gxtforms.client.config.FormConfiguration;

public interface FormBuilder<T extends FormPanel> {

    T constructPanel();
    
    T buildFormPanel(FormConfiguration formConfig, Layout layout);

    void bind(FormConfiguration formConfig, T formPanel, Object bean);

    T buildFormPanel(FormConfiguration formConfig, Object bean);

    T buildFormPanel(FormConfiguration formConfig);

}