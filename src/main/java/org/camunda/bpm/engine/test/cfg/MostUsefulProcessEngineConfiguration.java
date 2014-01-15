package org.camunda.bpm.engine.test.cfg;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;

import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.camunda.bpm.engine.impl.jobexecutor.JobHandler;
import org.camunda.bpm.engine.test.ProcessEngineTestRule;
import org.camunda.bpm.engine.test.ProcessEngineTestWatcher;
import org.camunda.bpm.engine.test.mock.MockExpressionManager;

public class MostUsefulProcessEngineConfiguration extends StandaloneInMemProcessEngineConfiguration {

    public static MostUsefulProcessEngineConfiguration mostUsefulProcessEngineConfiguration() {
        return new MostUsefulProcessEngineConfiguration();
    }

    public MostUsefulProcessEngineConfiguration() {
        this.history = "full";
        this.databaseSchemaUpdate = DB_SCHEMA_UPDATE_TRUE;
        this.jobExecutorActivate = false;
        this.expressionManager = new MockExpressionManager();
        this.setCustomPostBPMNParseListeners(new ArrayList<BpmnParseListener>());
        this.setCustomJobHandlers(new ArrayList<JobHandler>());
    }

    public void addCustomJobHandler(final JobHandler jobHandler) {
        checkArgument(jobHandler != null, "jobHandler must not be null!");
        getCustomJobHandlers().add(jobHandler);
    }

    public void addCustomPostBpmnParseListener(final BpmnParseListener bpmnParseListener) {
        checkArgument(bpmnParseListener != null, "bpmnParseListener must not be null!");
        getCustomPostBPMNParseListeners().add(bpmnParseListener);
    }

    public ProcessEngineTestRule buildProcessEngineRule() {
        return new ProcessEngineTestWatcher(buildProcessEngine());
    }
}
