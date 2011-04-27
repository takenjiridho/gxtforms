package com.googlecode.gxtforms.server;

@SuppressWarnings("serial")
public class NoCacheFormService extends FormServiceImpl {

    public NoCacheFormService() {
        super();
        useCache = false;
    }

}
