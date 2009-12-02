package com.googlecode.gxtforms.demo.client.examples;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.googlecode.gxtforms.client.config.FormConfiguration;
import com.googlecode.gxtforms.client.form.FormService;
import com.googlecode.gxtforms.client.form.FormServiceAsync;
import com.googlecode.gxtforms.client.form.GXTFormBuilder;

public class FieldSetFormExample extends LayoutContainer {

    private VerticalPanel vp;

    public FieldSetFormExample() {
        vp = new VerticalPanel();
        vp.setSpacing(10);

    }

    @Override
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        add(vp);

        FormServiceAsync service = GWT.create(FormService.class);
        service.getFormConfiguration(FieldSetForm.class.getName(), new AsyncCallback<FormConfiguration>() {
            public void onSuccess(FormConfiguration result) {
                addForm(result);
            }

            public void onFailure(Throwable caught) {
                MessageBox.alert("Error", caught.getMessage(), null);
            }
        });
    }

    public void addForm(FormConfiguration config) {
        final FieldSetForm form = new FieldSetForm();
        FormPanel panel = new GXTFormBuilder().buildFormPanel(config, form);
        Button submit = new Button("Test DataBinding", new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                MessageBox.alert("DataBinding", form.getName() + ":" + form.getCar() + ":" + form.getCar2() + ":" + form.getDescription()
                        + ":" + form.getCreatedAt() + ":" + form.getEmail() + ":" + form.getPhone(), null);
            }
        });
        
        panel.addButton(submit);
        vp.add(new Label("The name field has a custom style added, and the description field is disabled."));
        vp.add(panel);
        vp.layout();
    }
}