package org.camunda.bpm.engine.test;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.ExternalTaskService;
import org.camunda.bpm.engine.FilterService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineDelegate;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.test.TestHelper;
import org.camunda.bpm.engine.impl.util.ClockUtil;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.util.Date;

/**
 * Rewrite of the ProcessEngineRule since TestWatchman is deprecated for
 * junit>4.9.
 *
 * @author Jan Galinski, Holisticon AG (jan.galinski@holisticon.de)
 */
public class ProcessEngineTestWatcher extends ChainedTestRule<ProcessEngineExternalResource, TestWatcher> implements ProcessEngineTestRule {

  /**
   * Holds the deploymentId after execution of @Deployment. can be used to query
   * the {@link RepositoryService}.
   */
  protected String deploymentId;

  /**
   * Like the old ProcessEngineRule ...
   */
  private final TestWatcher innerRule;

  /**
   * Use default engine.
   */
  public ProcessEngineTestWatcher(ProcessEngineDelegate processEngineDelegate) {
    super(new ProcessEngineExternalResource(processEngineDelegate));

    this.innerRule = new TestWatcher() {
      @Override
      protected void starting(Description description) {
        deploymentId = TestHelper.annotationDeploymentSetUp(outerRule.getProcessEngine(), description.getTestClass(), description.getMethodName());
      }

      @Override
      protected void finished(Description description) {
        TestHelper.annotationDeploymentTearDown(outerRule.getProcessEngine(), deploymentId, description.getTestClass(), description.getMethodName());
        ClockUtil.reset();
      }
    };
  }

  /**
   * Create a new rule instance for given process engine.
   *
   * @param processEngine the process engine
   */
  public ProcessEngineTestWatcher(final ProcessEngine processEngine) {
    this(new ProcessEngineDelegate(processEngine));
  }

  @Override
  protected TestWatcher innerRule() {
    return innerRule;
  }

  @Override
  public void setCurrentTime(final Date currentTime) {
    ClockUtil.setCurrentTime(currentTime);
  }

  @Override
  public ProcessEngine getProcessEngine() {
    return outerRule.getProcessEngine();
  }

  @Override
  public String getDeploymentId() {
    return deploymentId;
  }

  @Override
  public RepositoryService getRepositoryService() {
    return outerRule.getRepositoryService();
  }

  @Override
  public RuntimeService getRuntimeService() {
    return outerRule.getRuntimeService();
  }

  @Override
  public FormService getFormService() {
    return outerRule.getFormService();
  }

  @Override
  public TaskService getTaskService() {
    return outerRule.getTaskService();
  }

  @Override
  public HistoryService getHistoryService() {
    return outerRule.getHistoryService();
  }

  @Override
  public IdentityService getIdentityService() {
    return outerRule.getIdentityService();
  }

  @Override
  public ManagementService getManagementService() {
    return outerRule.getManagementService();
  }

  @Override
  public AuthorizationService getAuthorizationService() {
    return outerRule.getAuthorizationService();
  }

  @Override
  public CaseService getCaseService() {
    return outerRule.getCaseService();
  }

  @Override
  public FilterService getFilterService() {
    return outerRule.getFilterService();
  }

  @Override
  public ExternalTaskService getExternalTaskService() {
    return outerRule.getExternalTaskService();
  }

  @Override
  public DecisionService getDecisionService() {
    return outerRule.getDecisionService();
  }

}
