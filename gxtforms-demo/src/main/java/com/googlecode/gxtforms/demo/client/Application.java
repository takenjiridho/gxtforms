package com.googlecode.gxtforms.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.gxtforms.client.FormService;
import com.googlecode.gxtforms.client.FormServiceAsync;
import com.googlecode.gxtforms.client.GXTFormBuilder;
import com.googlecode.gxtforms.client.config.FormConfiguration;

public class Application implements EntryPoint {

    public Application() {
    }

    @Override
    public void onModuleLoad() {
        FormServiceAsync service = GWT.create(FormService.class);
        service.getFormConfiguration(SimpleForm.class.getName(), new AsyncCallback<FormConfiguration>() {
            public void onSuccess(FormConfiguration result) {
                RootPanel.get().add(new GXTFormBuilder().buildFormPanel(result));
            }

            public void onFailure(Throwable caught) {

            }
        });
        
    }

}
