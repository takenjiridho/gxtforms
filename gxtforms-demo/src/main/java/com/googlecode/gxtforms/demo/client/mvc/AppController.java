/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.googlecode.gxtforms.demo.client.mvc;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.googlecode.gxtforms.demo.client.AppEvents;

public class AppController extends Controller {

  private AppView appView;

  public AppController() {
    appView = new AppView(this);

    registerEventTypes(AppEvents.Init);
  }

  public void handleEvent(AppEvent event) {
    if (event.getType() == AppEvents.Init) {
      forwardToView(appView, event);
    }
  }

}
