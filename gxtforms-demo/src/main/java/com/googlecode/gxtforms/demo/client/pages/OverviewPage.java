/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.googlecode.gxtforms.demo.client.pages;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.ListView;
import com.google.gwt.user.client.Element;
import com.googlecode.gxtforms.demo.client.Application;
import com.googlecode.gxtforms.demo.client.ExplorerModel;
import com.googlecode.gxtforms.demo.client.model.Entry;

public class OverviewPage extends LayoutContainer {

  private ListView<ModelData> dataView;

  @Override
  protected void onRender(Element parent, int pos) {
    super.onRender(parent, pos);

    setScrollMode(Scroll.AUTO);

    ExplorerModel model = (ExplorerModel) Registry.get(Application.MODEL);
    ListStore<ModelData> store = new ListStore<ModelData>();
    store.add(model.getEntries());

    StringBuffer sb = new StringBuffer();
    sb.append("<tpl for=\".\">");
    sb.append("<div class='sample-box'>");
    sb.append("<div class='thumbd'>{image}</div>");
    sb.append("<div>{name}</div>");
    sb.append("</div></tpl>");

    dataView = new ListView<ModelData>();
    dataView.setItemSelector(".sample-box");
    dataView.setOverStyle("sample-over");
    dataView.setSelectStyle("none");
    dataView.setBorders(false);
    dataView.setStore(store);
    dataView.setTemplate(sb.toString());
    dataView.getSelectionModel().addSelectionChangedListener(new SelectionChangedListener<ModelData>() {
    
      @Override
      public void selectionChanged(SelectionChangedEvent<ModelData> se) {
        if (se.getSelectedItem() != null) {
          ModelData record = se.getSelectedItem();
          Entry entry = (Entry) record;
          Application.showPage(entry);
          dataView.getSelectionModel().deselectAll();
        }
      }
    });
    add(dataView);
  }
}
