package com.googlecode.gxtforms.server;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.gxtforms.client.config.FormConfiguration;
import com.googlecode.gxtforms.client.exceptions.FormConfigurationException;
import com.googlecode.gxtforms.client.form.FormService;

@SuppressWarnings("serial")
public class FormServiceImpl extends RemoteServiceServlet implements FormService {

    Map<String, FormConfiguration> cache = new HashMap<String, FormConfiguration>();

    boolean useCache = true;

    public FormConfiguration getFormConfiguration(String className) throws FormConfigurationException {
        FormConfiguration config;

        if (!useCache || (config = cache.get(className)) == null) {
            config = new FormConfiguration();
            try {
                Class<?> target = Class.forName(className);
                FormBean formBean = FormBeanUtils.initFormBean(target);
                config.setFieldConfigurations(formBean.getFields());
                config.setFormPanelConfiguration(formBean.getFormConfiguration());
                if (useCache) {
                    cache.put(className, config);
                }
            } catch (Exception e) {
                throw new FormConfigurationException("failed loading form class " + className, e);
            }
        }

        return config;
    }

    public void setUseCache(boolean useCache) {
        this.useCache = useCache;
    }

}
