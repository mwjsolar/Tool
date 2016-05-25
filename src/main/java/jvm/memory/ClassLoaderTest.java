package jvm.memory;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hzmawenjun on 2015/12/19.
 */
public class ClassLoaderTest {


    public static void main(String[] args) {
         ClassLoader classLoaderDef = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                if (StringUtils.isBlank(name))
                    return null;

                String fileName = name.substring(name.lastIndexOf(".")+1)+".class";
                InputStream is = getClass().getResourceAsStream(fileName);
                if (is == null)
                    return super.loadClass(name);

                try {
                    if (is.available() > 0) {
                        byte[] buf = new byte[is.available()];
                        int len = is.read(buf);
                        return defineClass(name, buf, 0, len);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        try {
            Class classDef = classLoaderDef.loadClass("jvm.memory.ClassDefTest");
            Object classDefTest = classDef.newInstance();

            System.out.println(classDefTest.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

