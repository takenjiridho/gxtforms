package com.googlecode.gxtforms.demo.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.maven.jxr.JavaCodeTransform;
import org.apache.maven.jxr.log.Log;
import org.apache.maven.jxr.pacman.FileManager;
import org.apache.maven.jxr.pacman.PackageManager;

@SuppressWarnings("serial")
public class CodeServlet extends HttpServlet {

    Map<String, String> cache = new HashMap<String, String>();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = "/com/googlecode/gxtforms/demo/client/examples" + req.getPathInfo();
        String out;
        
        if ((out = cache.get(path)) == null) {
            InputStream codeStream = null;
            try {
                codeStream = getClass().getResourceAsStream(path);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
    
            PackageManager packageManager = new PackageManager(new Log() {
    
                public void debug(String message) {
                }
    
                public void error(String message) {
                }
    
                public void info(String message) {
                }
    
                public void warn(String message) {
                }
    
            }, new FileManager());
            
            JavaCodeTransform codeTransform = new JavaCodeTransform(packageManager);
            StringWriter writer = new StringWriter();
            writer.write("<pre>");
            codeTransform.transform(new InputStreamReader(codeStream), writer, Locale.ENGLISH, "ISO-8859-1", "ISO-8859-1", "", "",
                    false, false);
            writer.write("<pre>");
            out = writer.toString();
            cache.put(path, out);
        }
        
        resp.getWriter().write(out);
    }

}
