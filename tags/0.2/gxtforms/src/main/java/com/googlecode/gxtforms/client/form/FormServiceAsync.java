package com.googlecode.gxtforms.client.form;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.gxtforms.client.config.FormConfiguration;

public interface FormServiceAsync {

    void getFormConfiguration(String className, AsyncCallback<FormConfiguration> callback);
    
}
