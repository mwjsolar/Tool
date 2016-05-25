package delegete.javaagent;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Created by hzmawenjun on 2016/3/27.
 */
@Target({TYPE, FIELD, METHOD})
@Retention(CLASS)
public @ interface Agent {
    /**
     * inject name
     * @return
     */
    String name() default "";
}
