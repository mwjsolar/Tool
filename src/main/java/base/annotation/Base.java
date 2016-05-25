package base.annotation;

import java.lang.annotation.*;

/**
 * Created by hzmawenjun on 2016/5/10.
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
public @interface Base {
    int test() default 1;
}
