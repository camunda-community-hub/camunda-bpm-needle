package org.camunda.bpm.extension.engine.test;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.ProcessEngineDelegate;
import org.camunda.bpm.engine.ProcessEngineServiceProvider;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.junit.rules.ExternalResource;

/**
 * JUnit {@link org.junit.rules.ExternalResource} that provides process engine services.
 * <p/>
 * Can be initialized in three ways:
 * <ol>
 *   <li><b>Lazy</b> with a ProcessEngineConfiguration, the engine is created when the test is started. Before the test is started, access to any of the services results in an IllegalStateException. </li>
 *   <li><b>Eager</b> with a ProcessEngineConfiguration, the engine is initialized immediately</li>
 *   <li><b>Delegate</b> processEngine is initialized externally and just decorated by the resource. Useful when chaining rules.</li>
 * </ol>
 */
public class ProcessEngineExternalResource extends ExternalResource implements ProcessEngineServiceProvider {

  protected final ProcessEngineDelegate delegate;

  public ProcessEngineExternalResource(final ProcessEngineDelegate delegate) {
    this.delegate = delegate;
  }

  public ProcessEngineExternalResource(final ProcessEngineConfiguration processEngineConfiguration) {
    this(new ProcessEngineDelegate(processEngineConfiguration));
  }

  public ProcessEngineExternalResource(final ProcessEngineConfiguration processEngineConfiguration, boolean initialize) {
    this(new ProcessEngineDelegate(processEngineConfiguration, initialize));
  }

  /**
   *
   * @param processEngine the internally wrapped process engine
   */
  public ProcessEngineExternalResource(final ProcessEngine processEngine) {
    this(new ProcessEngineDelegate(processEngine));
  }

  @Override
  protected void before() throws Throwable {
    super.before();
    delegate.initProcessEngine();
  }

  @Override
  protected void after() {
    super.after();
    delegate.closeProcessEngine();
  }

  /**
   * @return the internally wrapped engine.
   */
  public ProcessEngine getProcessEngine() {
    return delegate.getProcessEngine();
  }

  @Override
  public RepositoryService getRepositoryService() {
    return delegate.getRepositoryService();
  }

  @Override
  public RuntimeService getRuntimeService() {
    return delegate.getRuntimeService();
  }

  @Override
  public FormService getFormService() {
    return delegate.getFormService();
  }

  @Override
  public TaskService getTaskService() {
    return delegate.getTaskService();
  }

  @Override
  public HistoryService getHistoryService() {
    return delegate.getHistoryService();
  }

  @Override
  public IdentityService getIdentityService() {
    return delegate.getIdentityService();
  }

  @Override
  public ManagementService getManagementService() {
    return delegate.getManagementService();
  }

  @Override
  public AuthorizationService getAuthorizationService() {
    return delegate.getAuthorizationService();
  }

  protected ProcessEngineDelegate getProcessEngineDelegate() {
    return delegate;
  }
}
