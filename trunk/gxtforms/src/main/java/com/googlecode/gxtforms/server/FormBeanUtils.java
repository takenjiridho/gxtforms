package com.googlecode.gxtforms.server;

import com.googlecode.gxtforms.client.exceptions.FormBeanException;

public class FormBeanUtils {

    /**
     * Instantiates a class if it's a FormBean and returns this instance. If the
     * target class is not a FormBean, then a wrapper is constructed and
     * returned.
     * 
     * @param target
     * @return
     * @throws FormBeanException if class or wrapper instantiation fails
     */
    public static FormBean initFormBean(Class<?> target) throws FormBeanException {
        try {
            FormBean formBean;
            if (FormBean.class.isAssignableFrom(target)) {
                formBean = (FormBean) target.newInstance();
            } else {
                formBean = new FormBeanImplAdapater(target);
            }
            return formBean;
        } catch (Exception e) {
            throw new FormBeanException("Failed instantiating form bean", e);
        }
    }
}
