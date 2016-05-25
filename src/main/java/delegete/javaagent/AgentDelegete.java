package delegete.javaagent;

import java.lang.instrument.Instrumentation;

/**
 * java agent
 * Created by hzmawenjun on 2016/3/24.
 */
public class AgentDelegete {
    public static void premain(String agentArgs,Instrumentation instrumentation) {
        System.out.println("agent:"+agentArgs);
        instrumentation.addTransformer(new AnnotationClassFormer());
    }
}
