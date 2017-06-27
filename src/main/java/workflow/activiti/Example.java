package workflow.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.*;
import org.junit.Assert;
import org.springframework.util.*;
import workflow.activiti.unit.ActivityConstant;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by mwjsolar on 17/1/6.
 */
public class Example {
    private static ProcessEngine processEngine;

    //部署id
    private static String deploymentId;


    @BeforeClass
    public static void setUp() {
        processEngine = ProcessEngines.getDefaultProcessEngine();

        Deployment deployment = processEngine.getRepositoryService()//获取流程定义和部署相关的Service
                .createDeployment()//创建部署对象
                .addClasspathResource("test.bpmn")
                .deploy();//完成部署
        deploymentId = deployment.getId();
        System.out.println("部署ID：" + deployment.getId());//部署ID:1
        System.out.println("部署时间：" + deployment.getDeploymentTime());//部署时间
    }


    @Test
    public void start() {
        ProcessInstance processInstance = processEngine.getRuntimeService() // 运行时流程实例Service
                .startProcessInstanceByKey("TestProcess"); // 数据库中流程定义表(act_re_prcdef)的KEY字段值；key对应对应 流程图里的process id的名字，使用Key值 启动，默认是按照最新版本的流程定义启动的

        Execution execution = processEngine.getRuntimeService().createExecutionQuery()
                .activityId(ActivityConstant.RECEIVABLE_TASK)
                .processInstanceId(processInstance.getProcessInstanceId())
                .singleResult();

        System.out.println("execute signal task");

        processEngine.getRuntimeService().signal(execution.getId());

        assertEquals(true,processInstance.isEnded());
    }

    @Test
    public void findTask(){
        // 查询并且返回任务即可
        List<Task> taskList=processEngine.getTaskService() // 任务相关Service
                .createTaskQuery()  // 创建任务查询
                .list();
        for(Task task:taskList){
            System.out.println("任务ID:"+task.getId());
            System.out.println("任务名称："+task.getName());
            System.out.println("任务创建时间："+task.getCreateTime());
            System.out.println("任务委派人："+task.getAssignee());
            System.out.println("流程实例ID:"+task.getProcessInstanceId());
        }
    }

    @AfterClass
    public static void destroy() {
        System.out.println("destroy deployment");
        processEngine.getRepositoryService().deleteDeployment(deploymentId);
    }
}
