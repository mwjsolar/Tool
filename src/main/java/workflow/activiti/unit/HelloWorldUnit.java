package workflow.activiti.unit;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * Created by mwjsolar on 17/1/7.
 */
public class HelloWorldUnit implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("hello world");
    }
}
