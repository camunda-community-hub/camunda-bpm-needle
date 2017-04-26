package org.camunda.bpm.engine.test.needle;

import com.google.common.collect.Sets;
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
import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.ProcessEngineImpl;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.camunda.bpm.engine.test.function.GetProcessEngineConfiguration;
import org.needle4j.injection.InjectionProvider;
import org.needle4j.injection.InjectionProviderInstancesSupplier;

import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static org.needle4j.injection.InjectionProviders.providerForInstance;

/**
 * Supplier for camunda services. Holds processEngine internally and exposes all
 * services via {@link InjectionProvider}.
 *
 * @author Jan Galinski, Holisticon AG
 */
public class CamundaInstancesSupplier implements InjectionProviderInstancesSupplier, ProcessEngineServices {

  private final Set<InjectionProvider<?>> providers = Sets.newHashSet();

  private final ProcessEngine processEngine;

  public CamundaInstancesSupplier(final ProcessEngine processEngine) {
    checkArgument(processEngine != null);
    this.processEngine = processEngine;

    providers.add(providerForInstance(processEngine));
    providers.add(providerForInstance(getCommandExecutor()));
    providers.add(providerForInstance(getProcessEngineConfiguration()));

    providers.add(providerForInstance(getAuthorizationService()));
    providers.add(providerForInstance(getCaseService()));
    providers.add(providerForInstance(getDecisionService()));
    providers.add(providerForInstance(getExternalTaskService()));
    providers.add(providerForInstance(getFilterService()));
    providers.add(providerForInstance(getFormService()));
    providers.add(providerForInstance(getHistoryService()));
    providers.add(providerForInstance(getIdentityService()));
    providers.add(providerForInstance(getManagementService()));
    providers.add(providerForInstance(getRepositoryService()));
    providers.add(providerForInstance(getRuntimeService()));
    providers.add(providerForInstance(getTaskService()));
  }

  @Override
  public Set<InjectionProvider<?>> get() {
    return providers;
  }

  @Override
  public RepositoryService getRepositoryService() {
    return processEngine.getRepositoryService();
  }

  @Override
  public RuntimeService getRuntimeService() {
    return processEngine.getRuntimeService();
  }

  @Override
  public FormService getFormService() {
    return processEngine.getFormService();
  }

  @Override
  public TaskService getTaskService() {
    return processEngine.getTaskService();
  }

  @Override
  public HistoryService getHistoryService() {
    return processEngine.getHistoryService();
  }

  @Override
  public IdentityService getIdentityService() {
    return processEngine.getIdentityService();
  }

  @Override
  public ManagementService getManagementService() {
    return processEngine.getManagementService();
  }

  @Override
  public AuthorizationService getAuthorizationService() {
    return processEngine.getAuthorizationService();
  }

  @Override
  public CaseService getCaseService() {
    return processEngine.getCaseService();
  }

  @Override
  public FilterService getFilterService() {
    return processEngine.getFilterService();
  }

  @Override
  public ExternalTaskService getExternalTaskService() {
    return processEngine.getExternalTaskService();
  }

  @Override
  public DecisionService getDecisionService() {
    return processEngine.getDecisionService();
  }

  /**
   * @return the current configuration
   */
  public ProcessEngineConfiguration getProcessEngineConfiguration() {
    return GetProcessEngineConfiguration.INSTANCE.apply(processEngine);
  }

  /**
   * @return current process engine
   */
  public ProcessEngine getProcessEngine() {
    return processEngine;
  }

  /**
   * @return a command executor for the processEngine
   */
  public CommandExecutor getCommandExecutor() {
    return ((ProcessEngineImpl) getProcessEngine()).getProcessEngineConfiguration().getCommandExecutorSchemaOperations();
  }

}
