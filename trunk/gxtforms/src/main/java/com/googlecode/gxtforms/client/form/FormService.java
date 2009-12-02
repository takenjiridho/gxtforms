package com.googlecode.gxtforms.client.form;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.googlecode.gxtforms.client.config.FormConfiguration;

@RemoteServiceRelativePath("FormService")
public interface FormService extends RemoteService {
    
    
    /**
     * Return a FormConfiguration generated from a given class.
     * @param className the fully qualified class name for a class.  This value is passed to Class.forName(String name)
     * @return
     */
    FormConfiguration getFormConfiguration(String className);

}
