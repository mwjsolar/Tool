package base.annotation;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;

/**
 * Created by hzmawenjun on 2016/5/10.
 */
public class TestAnotation {

    @BaseDetail
    public static class InnerClass {
        public InnerClass() {
        }
    }

    public static void main(String[] args) {
        InnerClass innerClass = new InnerClass();
        Annotation[] baseDetailAnnotation = AnnotationUtils.getAnnotations(Service.class);
        System.out.println(baseDetailAnnotation[0].getClass());
        System.out.println(innerClass.getClass().isAnnotationPresent(Base.class));
    }
}
