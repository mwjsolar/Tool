package workflow.activiti.unit;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * Created by mwjsolar on 17/1/10.
 */
public class ReceivaeTaskUnit implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) throws Exception {
        System.out.println("receivable task");
    }
}
