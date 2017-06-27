package rules.drools;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by mwjsolar on 16/10/18.
 */

public class DroolExecutor {

    private final static Logger log = LoggerFactory.getLogger(DroolExecutor.class);
    public DroolsResult executeRule(DroolsObject factData,String drlFilePath) {
        KnowledgeBuilder knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        knowledgeBuilder.add(ResourceFactory.newClassPathResource(drlFilePath, DroolExecutor.class), ResourceType.DRL);
        KnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase();

        if (knowledgeBuilder.hasErrors()) {
            for (KnowledgeBuilderError err : knowledgeBuilder.getErrors()) {
                log.error("rules.rule explain error :[{}]",err);
            }
            throw new IllegalStateException("drl load errors");
        }
        kBase.addKnowledgePackages(knowledgeBuilder.getKnowledgePackages());

        //3.执行规则
        StatefulKnowledgeSession session = kBase.newStatefulKnowledgeSession();
        session.insert(factData);
        DroolsResult result = new DroolsResult();
        session.setGlobal("dr", result);

        session.fireAllRules();
        session.dispose();//释放相关内存资源
        return result;
    }

    public static void main(String[] args) {
        DroolExecutor drools = new DroolExecutor();
        DroolsObject droolsObject = new DroolsObject();
        droolsObject.setName("mwj");
        drools.executeRule(droolsObject,"drools.drl");
    }

}
