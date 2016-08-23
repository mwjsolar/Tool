package jvm.memory;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by mwjsolar on 16/8/22.
 */
public class MultiClassLoader {

    private final String CLASS_NAME = "";
    private final String METHOD_NAME = "";
    public void directLoadClass() {
        File file = new File("") ;
        try {
            URLClassLoader classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});
            Class fClass = classLoader.loadClass(CLASS_NAME);
            Object object = fClass.newInstance();
            Method fThthod = fClass.getDeclaredMethod(METHOD_NAME);
            Object result = fThthod.invoke(object, null);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
