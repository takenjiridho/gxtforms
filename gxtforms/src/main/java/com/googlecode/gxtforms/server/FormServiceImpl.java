package com.googlecode.gxtforms.server;

import java.util.HashMap;

import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.gxtforms.client.FormConfigurationException;
import com.googlecode.gxtforms.client.FormService;
import com.googlecode.gxtforms.client.config.FormConfiguration;

@SuppressWarnings("serial")
public class FormServiceImpl extends RemoteServiceServlet implements FormService {

    Map<String, FormConfiguration> cache = new HashMap<String, FormConfiguration>();
    
    public FormConfiguration getFormConfiguration(String className) {
        FormConfiguration config;
        
        if ((config = cache.get(className)) == null) {
            config = new FormConfiguration();
            try {
                Class<?> target = Class.forName(className);
                FormBean formBean;
                if (FormBean.class.isAssignableFrom(target)) {
                    formBean = (FormBean) target.newInstance();
                } else {
                    formBean = new FormBeanImplAdapater(target);
                }
                config.setFieldConfigurations(formBean.getFields());
                config.setFormPanelConfiguration(formBean.getFormConfiguration());
                
                cache.put(className, config);
            } catch (Exception e) {
                throw new FormConfigurationException("failed loading form class " + className, e);
            }
        }
        
        return config;
        
    }

}
