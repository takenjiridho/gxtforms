/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.googlecode.gxtforms.demo.client.pages;


import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.googlecode.gxtforms.demo.client.model.Entry;

public class Page extends TabPanel {

  protected Entry entry;

  public LayoutContainer getContent() {
    return entry.getExample();
  }

  public Page(final Entry entry) {
    this.entry = entry;

    setTabPosition(TabPosition.BOTTOM);
    setBorderStyle(false);
    setBodyBorder(false);

    addListener(Events.Adopt, new Listener<ComponentEvent>() {
      public void handleEvent(ComponentEvent be) {
        if(getParent() != null && getParent() instanceof TabItem) {
          TabItem t = (TabItem) getParent();
          t.setHideMode(entry.getHideMode());
        }
      }
    });
    
    TabItem demo = new TabItem();
    demo.setScrollMode(Scroll.AUTO);
    if (entry.isFill()) {
      demo.setLayout(new FitLayout());
      demo.setScrollMode(Scroll.NONE);
    }
    
    demo.setHideMode(entry.getHideMode());

    demo.setText("Demo");
    demo.add(entry.getExample());
    add(demo);
    
    if (entry.isClosable()) {
      TabItem source = new TabItem();
      source.setText("Client Code Source");
      source.setUrl(entry.getExampleUrl());
      add(source);
      TabItem bean = new TabItem();
      bean.setText("Annotations Source");
      bean.setUrl(entry.getBeanUrl());
      add(bean);
    }
  }

}
