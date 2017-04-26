package org.camunda.bpm.extension.needle;

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
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.test.ChainedTestRule;
import org.camunda.bpm.engine.test.ProcessEngineTestRule;
import org.camunda.bpm.engine.test.ProcessEngineTestWatcher;
import org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions;
import org.camunda.bpm.engine.test.function.GetProcessEngineConfiguration;
import org.camunda.bpm.engine.test.needle.CamundaInstancesSupplier;
import org.junit.rules.RuleChain;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.needle4j.injection.InjectionProvider;
import org.needle4j.injection.InjectionProviderInstancesSupplier;
import org.needle4j.injection.InjectionProviders;
import org.needle4j.junit.testrule.NeedleTestRule;

import javax.sql.DataSource;
import java.util.Date;
import java.util.Set;

import static org.needle4j.junit.NeedleBuilders.needleMockitoTestRule;

/**
 * Combines the {@link org.camunda.bpm.engine.test.ProcessEngineRule} and the
 * {@link org.needle4j.junit.testrule.NeedleTestRule}via {@link RuleChain}.
 * Camunda Services can be injected in test instances and @Deployment-annotated
 * test methods are interpreted.
 *
 * @author Jan Galinski, Holisticon AG (jan.galinski@holisticon.de)
 */
public class ProcessEngineNeedleRule extends ChainedTestRule<NeedleTestRule, ProcessEngineTestWatcher> implements ProcessEngineTestRule {

  /**
   * @param testInstance the test instance to inject to
   * @return builder to create the rule
   */
  public static ProcessEngineNeedleRuleBuilder fluentNeedleRule(final Object testInstance) {
    return new ProcessEngineNeedleRuleBuilder(testInstance);
  }

  private final ProcessEngineTestWatcher innerRule;

  ProcessEngineNeedleRule(final Object testInstance, final ProcessEngine processEngine, final InjectionProviderInstancesSupplier additionalProvidersSupplier) {
    // @formatter:off
    super(needleMockitoTestRule(testInstance).addSupplier(new CamundaInstancesSupplier(processEngine)).addSupplier(additionalProvidersSupplier).build());
    // @formatter:on
    this.innerRule = new ProcessEngineTestWatcher(processEngine);
  }

  ProcessEngineNeedleRule(final Object testInstance, final ProcessEngineConfiguration configuration, final Set<InjectionProvider<?>> injectionProviders) {
    this(testInstance, configuration.buildProcessEngine(), InjectionProviders.supplierForInjectionProviders(injectionProviders));
  }

  @Override
  public Statement apply(Statement base, Description description) {
    ProcessEngineAssertions.init(getProcessEngine());
    try {
      return super.apply(base, description);
    } finally {
      ProcessEngineAssertions.reset();
    }
  }

  @Override
  protected ProcessEngineTestWatcher innerRule() {
    return innerRule;
  }

  @Override
  public void setCurrentTime(Date currentTime) {
    innerRule.setCurrentTime(currentTime);
  }

  @Override
  public String getDeploymentId() {
    return innerRule.getDeploymentId();
  }

  @Override
  public ProcessEngine getProcessEngine() {
    return innerRule.getProcessEngine();
  }

  @Override
  public RepositoryService getRepositoryService() {
    return innerRule.getRepositoryService();
  }

  @Override
  public RuntimeService getRuntimeService() {
    return innerRule.getRuntimeService();
  }

  @Override
  public FormService getFormService() {
    return innerRule.getFormService();
  }

  @Override
  public TaskService getTaskService() {
    return innerRule.getTaskService();
  }

  @Override
  public HistoryService getHistoryService() {
    return innerRule.getHistoryService();
  }

  @Override
  public IdentityService getIdentityService() {
    return innerRule.getIdentityService();
  }

  @Override
  public ManagementService getManagementService() {
    return innerRule.getManagementService();
  }

  public ProcessEngineConfiguration getProcessEngineConfiguration() {
    return GetProcessEngineConfiguration.INSTANCE.apply(getProcessEngine());
  }

  public DataSource getProcessEngineDataSource() {
    return getProcessEngineConfiguration().getDataSource();
  }

  @Override
  public AuthorizationService getAuthorizationService() {
    return innerRule.getAuthorizationService();
  }

  @Override
  public CaseService getCaseService() {
    return innerRule.getCaseService();
  }

  @Override
  public FilterService getFilterService() {
    return innerRule.getFilterService();
  }

  @Override
  public ExternalTaskService getExternalTaskService() {
    return innerRule.getExternalTaskService();
  }

  @Override
  public DecisionService getDecisionService() {
    return innerRule.getDecisionService();
  }

}
