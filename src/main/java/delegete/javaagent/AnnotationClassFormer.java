package delegete.javaagent;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * class file transformer
 * Created by hzmawenjun on 2016/3/27.
 */
public class AnnotationClassFormer implements ClassFileTransformer {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private final ClassPool classPool;
    public AnnotationClassFormer() {
        classPool = ClassPool.getDefault();

    }

    @Override
    public byte[] transform(ClassLoader loader,
                            String className,
                                Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {

        String replaceClassName = className.replace('/', '.');
        try {
            CtClass ctClass = classPool.get(replaceClassName);
            CtMethod[] ctMethodArr = ctClass.getMethods();
            for (CtMethod ctMethod : ctMethodArr) {
                if (ctMethod.getAnnotation(Agent.class) != null)
                    handleAgentMethod(ctMethod);
            }
            return ctClass.toBytecode();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    private void handleAgentMethod(CtMethod ctMethod) throws CannotCompileException {
        ctMethod.addLocalVariable("startTime",CtClass.longType);
        ctMethod.insertBefore(" startTime = System.currentTimeMillis();" +
                " System.out.println(\"start time :\" +startTime);");
        ctMethod.addLocalVariable("endTime",CtClass.longType);
        ctMethod.insertAfter(" endTime = System.currentTimeMillis();  " +
                " System.out.println(\"execute time :\" +endTime);");

    }
}
