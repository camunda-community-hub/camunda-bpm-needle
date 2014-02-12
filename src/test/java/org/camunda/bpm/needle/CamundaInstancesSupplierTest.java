package org.camunda.bpm.needle;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.test.function.CreateConfigurationFromResource;
import org.camunda.bpm.engine.test.needle.CamundaInstancesSupplier;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.needle4j.junit.NeedleRule;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.needle4j.injection.InjectionProviders.providersForInstancesSuppliers;

public class CamundaInstancesSupplierTest {

  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  @Rule
  public final NeedleRule needle = new NeedleRule(providersForInstancesSuppliers(new CamundaInstancesSupplier(CreateConfigurationFromResource.INSTANCE.buildProcessEngine())));

  @Inject
  private ManagementService managementService;

  @Inject
  private IdentityService identityService;

  @Inject
  private RepositoryService repositoryService;

  @Inject
  private RuntimeService runtimeService;

  @Inject
  private FormService formService;

  @Inject
  private HistoryService historyService;

  @Inject
  private TaskService taskService;

  @Test
  public void shouldInjectManagementService() {
    assertThat(managementService.createJobQuery().count(), is(0L));
  }

  @Test
  public void shouldInjectHistoryService() {
    assertThat(historyService.createHistoricActivityInstanceQuery().count(), is(0L));
  }

  @Test
  public void shouldInjectIdentityService() {
    assertThat(identityService.createUserQuery().count(), is(0L));
  }

  @Test
  public void shouldInjectFormService() {

    thrown.expect(ProcessEngineException.class);

    // throws exception when real service, fails for mock
    assertThat(formService.getRenderedStartForm("a"), notNullValue());
  }

  @Test
  public void shouldInjectRepositoryService() {
    assertThat(repositoryService.createProcessDefinitionQuery().count(), is(0L));
  }

  @Test
  public void shouldInjectRuntimeService() {
    assertThat(runtimeService.createExecutionQuery().count(), is(0L));
  }

  @Test
  public void shouldInjectTaskService() {
    assertThat(taskService.createTaskQuery().count(), is(0L));
  }

}
