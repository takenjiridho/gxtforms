/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.googlecode.gxtforms.demo.client;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.TreeModel;
import com.googlecode.gxtforms.demo.client.examples.CustomStyleFormExample;
import com.googlecode.gxtforms.demo.client.examples.FieldSetFormExample;
import com.googlecode.gxtforms.demo.client.examples.IndexedFormExample;
import com.googlecode.gxtforms.demo.client.examples.NestedBeansFormExample;
import com.googlecode.gxtforms.demo.client.examples.SimpleFormExample;
import com.googlecode.gxtforms.demo.client.images.ExampleImages;
import com.googlecode.gxtforms.demo.client.model.Category;
import com.googlecode.gxtforms.demo.client.model.Entry;
import com.googlecode.gxtforms.demo.client.pages.OverviewPage;

@SuppressWarnings("serial")
public class ExplorerModel extends BaseTreeModel {

    protected List<Entry> entries = new ArrayList<Entry>();

    public ExplorerModel() {
        Category grids = new Category("Dynamic Forms");
        grids.add("Simple Form", new SimpleFormExample(), ExampleImages.BUNDLE.simpleForm().getHTML());
        grids.add("Indexed Fields", new IndexedFormExample(), ExampleImages.BUNDLE.indexedForm().getHTML());
        grids.add("Custom Styles", new CustomStyleFormExample(), ExampleImages.BUNDLE.customStylesForm().getHTML());
        grids.add("Field Sets", new FieldSetFormExample(), ExampleImages.BUNDLE.fieldSetForm().getHTML());
        grids.add("Nested Beans", new NestedBeansFormExample(), ExampleImages.BUNDLE.nestedBeansForm().getHTML());
        add(grids);
        set("overview", new Entry("Overview", new OverviewPage(), null, true, false));
        loadEntries(this);
    }

    public Entry findEntry(String name) {
        if (get(name) != null) {
            return (Entry) get(name);
        }
        for (Entry entry : getEntries()) {
            if (name.equals(entry.getId())) {
                return entry;
            }
        }
        return null;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    
    private void loadEntries(TreeModel model) {
        for (ModelData child : model.getChildren()) {
          if (child instanceof Entry) {
            entries.add((Entry) child);
          } else if (child instanceof Category) {
            loadEntries((Category) child);
          }
        }
      }    
}
