package com.googlecode.gxtforms.client.components;

import java.util.HashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.layout.LayoutData;
import com.google.gwt.user.client.ui.Widget;

/**
 * Extends GXT Form Panel to facilitate looking up fields and field sets.
 */
public class IndexedFormPanel extends FormPanel {

    Map<String, Field<?>> fieldIndex = new HashMap<String, Field<?>>();
    Map<String, FieldSet> fieldSetIndex = new HashMap<String, FieldSet>();
    
    public IndexedFormPanel() {
        super();
    }
    
    /**
     * Returns the FieldSet with a given title, or null.
     *
     * @param title
     * @return
     */
    public FieldSet getFieldSet(String title) {
        return fieldSetIndex.get(title);
    }
    
    /**
     * Returns the Field with a given name, or null.
     * 
     * @param name
     * @return
     */
    public Field<?> getField(String name) {
        return fieldIndex.get(name);
    }

    @Override
    public boolean add(Widget widget) {
        indexWidget(widget);
        return super.add(widget);
    }
    
    
    @Override
    public boolean add(Widget widget, LayoutData layoutData) {
        indexWidget(widget);
        return super.add(widget, layoutData);
    }

    /**
     * Adds an entry in the field or field set lookup map based on the type of widget added.
     * <ul><li>Fields are indexed by name
     * <li>FieldSets are indexed by title
     * </ul>
     * The name or title should be unique within the form panel.  If not, the last entry "wins."
     * 
     * @param widget a widget being added to the form
     */
    @SuppressWarnings("unchecked")
    private void indexWidget(Widget widget) {
        if (widget instanceof Field) {
            Field field = (Field) widget;
            fieldIndex.put(field.getName(), field);
        } else if (widget instanceof FieldSet) {
            FieldSet fieldSet = (FieldSet) widget;
            fieldSetIndex.put(fieldSet.getTitle(), fieldSet);
        }
    }
    
}
