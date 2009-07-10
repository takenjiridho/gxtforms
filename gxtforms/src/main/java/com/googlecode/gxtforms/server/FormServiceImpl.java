package com.googlecode.gxtforms.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.gxtforms.client.FormConfigurationException;
import com.googlecode.gxtforms.client.FormService;
import com.googlecode.gxtforms.client.config.FormConfiguration;

@SuppressWarnings("serial")
public class FormServiceImpl extends RemoteServiceServlet implements FormService {

    public FormConfiguration getFormConfiguration(String className) {
        FormConfiguration config = new FormConfiguration();
        try {
            Class<?> target = Class.forName(className);
            if (Form.class.isAssignableFrom(target)) {
                config.setFieldConfigurations(((Form) target.newInstance()).getFields());
            } else {
                config.setFieldConfigurations(new FormImplAdapater(target).getFields());
            }
            return config;
        } catch (Exception e) {
            throw new FormConfigurationException("failed loading form class " + className, e);
        }
    }

}
