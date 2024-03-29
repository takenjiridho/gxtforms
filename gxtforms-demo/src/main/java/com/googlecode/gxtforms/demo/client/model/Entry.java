/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.googlecode.gxtforms.demo.client.model;

import com.extjs.gxt.ui.client.Style.HideMode;
import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.googlecode.gxtforms.demo.client.Application;

public class Entry extends BaseTreeModel {

  private String name;
  private boolean fill;
  private boolean closable = true;
  private HideMode hideMode = HideMode.DISPLAY;

  public Entry(String name, LayoutContainer example, String image) {
    this.name = name;
    set("name", name);
    set("image", image);
    set("example", example);
  }

  public Entry(String name, LayoutContainer example, String image, boolean fill) {
    this(name, example, image);
    this.fill = fill;
  }

  public Entry(String name, LayoutContainer example, String image, boolean fill, boolean closable) {
    this(name, example, image, fill);
    this.closable = closable;
  }

  public Entry(String name, LayoutContainer example, String image, boolean fill, boolean closable, HideMode hideMode) {
    this(name, example, image, fill, closable);
    this.setHideMode(hideMode);
  }

  protected Entry() {

  }

  public LayoutContainer getExample() {
    return (LayoutContainer) get("example");
  }

  public String getId() {
    if (name.equals("% Columns")) {
      return "percentcolumns";
    }
    return name.replaceAll(" ", "").toLowerCase();
  }

  public HideMode getHideMode() {
    return hideMode;
  }

  public String getName() {
    return (String) get("name");
  }

  public String getExampleUrl() {
    Object o = (Object) get("example");
    String name = o.getClass().getName();

    name = name.substring(name.lastIndexOf("examples.") + 9);
    name = "code/" + name + ".java";
//    name = name.replaceFirst("\\.", "/");

    if (!Application.isExplorer()) {
      name = "../../" + name;
    }

    return name;
  }
  
  public String getBeanUrl() {
      return getExampleUrl().replace("Example", "");
  }
  

  public boolean isClosable() {
    return closable;
  }

  public boolean isFill() {
    return fill;
  }

  public void setFill(boolean fill) {
    this.fill = fill;
  }

  public void setHideMode(HideMode hideMode) {
    this.hideMode = hideMode;
  }

  public String toString() {
    return getName();
  }

}
