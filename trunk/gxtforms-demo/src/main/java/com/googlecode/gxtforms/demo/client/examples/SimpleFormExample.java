package com.googlecode.gxtforms.demo.client.examples;

import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
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
                vp.add(new GXTFormBuilder().buildFormPanel(result));
                vp.layout();
            }

            public void onFailure(Throwable caught) {

            }
        });
    }

}