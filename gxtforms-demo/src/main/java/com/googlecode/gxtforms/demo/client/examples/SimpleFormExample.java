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
import com.googlecode.gxtforms.client.FormService;
import com.googlecode.gxtforms.client.FormServiceAsync;
import com.googlecode.gxtforms.client.GXTFormBuilder;
import com.googlecode.gxtforms.client.config.FormConfiguration;

public class SimpleFormExample extends LayoutContainer {

    private VerticalPanel vp;

    public SimpleFormExample() {
        vp = new VerticalPanel();
        vp.setSpacing(10);

    }

    @Override
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        add(vp);

        FormServiceAsync service = GWT.create(FormService.class);
        service.getFormConfiguration(SimpleForm.class.getName(), new AsyncCallback<FormConfiguration>() {
            public void onSuccess(FormConfiguration result) {
                addForm(result);
            }

            public void onFailure(Throwable caught) {
                MessageBox.alert("Error", caught.getMessage(), null);
            }
        });
    }

    public void addForm(FormConfiguration config) {
        final SimpleForm form = new SimpleForm();
        FormPanel panel = new GXTFormBuilder().buildFormPanel(config, form);
        Button submit = new Button("Test DataBinding", new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                MessageBox.alert("DataBinding", form.getName() + ":" + form.getCar() + ":" + form.getCar2() + ":" + form.getDescription()
                        + ":" + form.getCreatedAt() + ":" + form.getEmail() + ":" + form.getPhone(), null);
            }
        });
        panel.addButton(submit);
        vp.add(panel);
        vp.layout();
    }

}