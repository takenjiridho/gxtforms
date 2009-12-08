package com.googlecode.gxtforms.demo.client;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.user.client.Window;
import com.googlecode.gxtforms.demo.client.model.Entry;
import com.googlecode.gxtforms.demo.client.mvc.AppController;
import com.googlecode.gxtforms.demo.client.mvc.AppView;
import com.googlecode.gxtforms.demo.client.mvc.ContentController;
import com.googlecode.gxtforms.demo.client.mvc.NavigationController;

public class Application implements EntryPoint {

    public static String MODEL = "model";
    ExplorerModel model;
    Dispatcher dispatcher;

    public Application() {
    }

    public static boolean isExplorer() {
        String test = Window.Location.getPath();
        if (test.indexOf("pages") != -1) {
            return false;
        }
        return true;
    }

    @Override
    public void onModuleLoad() {

        if (!GWT.isScript()) {
            GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
                public void onUncaughtException(Throwable e) {
                    e.printStackTrace();
                }
            });
        }

        model = new ExplorerModel();
        Registry.register(MODEL, model);

        dispatcher = Dispatcher.get();
        dispatcher.addController(new AppController());
        dispatcher.addController(new NavigationController());
        dispatcher.addController(new ContentController());
        dispatcher.dispatch(AppEvents.Init);

        String hash = Window.Location.getHash();

        showPage(model.findEntry("overview"));

        Viewport v = Registry.get(AppView.VIEWPORT);
        v.layout(true);

        if (!"".equals(hash)) {
            hash = hash.substring(1);
            Entry entry = model.findEntry(hash);
            if (entry != null) {
                showPage(entry);
            }
        }

    }

    public static void showPage(Entry entry) {
        AppEvent appEvent = new AppEvent(AppEvents.ShowPage, entry);
        appEvent.setHistoryEvent(true);
        appEvent.setToken(entry.getId());
        Dispatcher.forwardEvent(appEvent);
      }
    
    
}
