package com.googlecode.gxtforms.demo.client.examples;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.googlecode.gxtforms.client.components.IndexedFormPanel;
import com.googlecode.gxtforms.client.config.FormConfiguration;
import com.googlecode.gxtforms.client.form.FormService;
import com.googlecode.gxtforms.client.form.FormServiceAsync;
import com.googlecode.gxtforms.client.form.GXTFormBuilder;

public class NestedBeansFormExample extends LayoutContainer {

    private VerticalPanel vp;

    public NestedBeansFormExample() {
        vp = new VerticalPanel();
        vp.setSpacing(10);

    }

    @Override
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        add(vp);

        FormServiceAsync service = GWT.create(FormService.class);
        service.getFormConfiguration(NestedBeansForm.class.getName(), new AsyncCallback<FormConfiguration>() {
            public void onSuccess(FormConfiguration result) {
                addForm(result);
            }

            public void onFailure(Throwable caught) {
                MessageBox.alert("Error", caught.getMessage(), null);
            }
        });
    }

    public void addForm(FormConfiguration config) {
        final NestedBeansForm form = new NestedBeansForm();
        IndexedFormPanel panel = (IndexedFormPanel) new GXTFormBuilder(true).buildFormPanel(config, form);
        Button submit = new Button("Test DataBinding", new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                MessageBox.alert("DataBinding", form.getFirstName() + ":" + form.getLastName() + ":"
                        + form.getAddress().getAddress1() + ":" + form.getAddress().getAddress2() + ":"
                        + form.getAddress().getZipCode() + ":" + form.getAddress().getState() + ":"
                        + form.getCarNick() + ":" + form.getCarType(), null);
            }
        });
        panel.addButton(submit);
        vp.add(panel);
        vp.layout();
    }
}