package org.camunda.bpm.engine.test.needle;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import de.akquinet.jbosscc.needle.injection.InjectionProvider;
import de.holisticon.toolbox.needle.provider.InjectionProviderInstancesSupplier;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.impl.ProcessEngineImpl;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.camunda.bpm.engine.impl.test.TestHelper;
import org.camunda.bpm.engine.test.cfg.MostUsefulProcessEngineConfiguration;
import org.camunda.bpm.engine.test.function.CreateConfigurationFromResource;
import org.camunda.bpm.engine.test.function.GetProcessEngineConfiguration;

import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static de.holisticon.toolbox.needle.provider.DefaultInstanceInjectionProvider.providerFor;

/**
 * Supplier for camunda services. Holds processEngine internally and exposes all
 * services via {@link InjectionProvider}.
 *
 * @author Jan Galinski, Holisticon AG
 */
public class CamundaInstancesSupplier implements InjectionProviderInstancesSupplier, ProcessEngineServiceProvider {

  private final Set<InjectionProvider<?>> providers = Sets.newHashSet();

  private final ProcessEngine processEngine;

  public CamundaInstancesSupplier(final ProcessEngine processEngine) {
    checkArgument(processEngine != null);
    this.processEngine = processEngine;

    providers.add(providerFor(processEngine));
    providers.add(providerFor(getRepositoryService()));
    providers.add(providerFor(getRuntimeService()));
    providers.add(providerFor(getFormService()));
    providers.add(providerFor(getTaskService()));
    providers.add(providerFor(getHistoryService()));
    providers.add(providerFor(getIdentityService()));
    providers.add(providerFor(getManagementService()));
    providers.add(providerFor(getAuthorizationService()));
    providers.add(providerFor(getCommandExecutor()));
    providers.add(providerFor(getProcessEngineConfiguration()));
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

  public ProcessEngineConfiguration getProcessEngineConfiguration() {
    return GetProcessEngineConfiguration.INSTANCE.apply(processEngine);
  }

  /**
   * @return current process engine
   */
  public ProcessEngine getProcessEngine() {
    return processEngine;
  }

  @Override
  public AuthorizationService getAuthorizationService() {
    return processEngine.getAuthorizationService();
  }

  public CommandExecutor getCommandExecutor() {
    return ((ProcessEngineImpl) getProcessEngine()).getProcessEngineConfiguration().getCommandExecutorSchemaOperations();
  }

}
