package com.googlecode.gxtforms.server;

import com.googlecode.gxtforms.client.FormService;
import com.googlecode.gxtforms.client.config.FormConfiguration;

public class FormServiceImpl implements FormService {

    public FormConfiguration getFormConfiguration(String className) {
        return new FormConfiguration();
    }

}
