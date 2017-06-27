package compiler;

import javassist.*;
import javassist.compiler.Javac;

/**
 * Created by mwjsolar on 16/9/19.
 */
public class Javaassist {
    public static void main(String[] args) {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = null;
        try {
             ctClass = classPool.getCtClass("compiler.Javaassist");
            CtMethod mthd = CtNewMethod.make("public Integer getInteger(int i) { i = i+ 1; return null; }", ctClass);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
