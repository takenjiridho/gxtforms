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
import com.googlecode.gxtforms.client.form.BasicFormBuilder;
import com.googlecode.gxtforms.client.form.FormService;
import com.googlecode.gxtforms.client.form.FormServiceAsync;

public class CustomStyleFormExample extends LayoutContainer {

    private VerticalPanel vp;

    public CustomStyleFormExample() {
        vp = new VerticalPanel();
        vp.setSpacing(10);

    }

    @Override
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        add(vp);

        FormServiceAsync service = GWT.create(FormService.class);
        service.getFormConfiguration(CustomStyleForm.class.getName(), new AsyncCallback<FormConfiguration>() {
            public void onSuccess(FormConfiguration result) {
                addForm(result);
            }

            public void onFailure(Throwable caught) {
                MessageBox.alert("Error", caught.getMessage(), null);
            }
        });
    }

    public void addForm(FormConfiguration config) {
        final CustomStyleForm form = new CustomStyleForm();
        form.setDescription("This is a pre-filled field.");
        FormPanel panel = new BasicFormBuilder().buildFormPanel(config, form);
        Button submit = new Button("Test DataBinding", new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                MessageBox.alert("DataBinding", form.getName() + ":" + form.getDescription(), null);
            }
        });
        
        panel.addButton(submit);
        vp.add(new Label("The name field has a custom style added, and the description field is disabled."));
        vp.add(panel);
        vp.layout();
    }
}