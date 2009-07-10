package com.googlecode.gxtforms.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class Application implements EntryPoint {

    public Application() {
    }

    @Override
    public void onModuleLoad() {
        RootPanel.get().add(new Label("here"));
    }

}
