package com.googlecode.gxtforms.client.form;

import com.googlecode.gxtforms.client.components.IndexedFormPanel;

public class IndexedFormBuilder extends GXTFormBuilder<IndexedFormPanel> {

    public IndexedFormBuilder() {
        super();
    }

    public IndexedFormPanel constructPanel() {
        return new IndexedFormPanel();
    }

}
