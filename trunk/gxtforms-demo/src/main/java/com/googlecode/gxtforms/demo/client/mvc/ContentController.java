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

public class ContentController extends Controller {

  private ContentView view;

  public ContentController() {
    registerEventTypes(AppEvents.Init, AppEvents.ShowPage);
  }

  public void initialize() {
    view = new ContentView(this);
  }

  public void handleEvent(AppEvent event) {
    forwardToView(view, event);
  }

}
