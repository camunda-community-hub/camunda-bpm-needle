package org.camunda.bpm.engine.test;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.function.CreateConfigurationFromResource;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by jangalinski on 04.02.14.
 *
 * @author Jan Galinski, Holisticon AG
 */
public class ProcessEngineTestWatcherTest {

  @Rule
  public final ProcessEngineTestWatcher processEngineTestWatcher = new ProcessEngineTestWatcher(CreateConfigurationFromResource.INSTANCE.buildProcessEngine());

  @Test
  @Deployment(resources = "test-process.bpmn")
  public void should_deploy_and_start_process() {
    Assert.assertThat(processEngineTestWatcher.getDeploymentId(), CoreMatchers.notNullValue());
    final ProcessInstance processInstance = processEngineTestWatcher.getRuntimeService().startProcessInstanceByKey("test-process");
    Assert.assertThat(processInstance, CoreMatchers.notNullValue());
    Assert.assertThat(processEngineTestWatcher.getRuntimeService().createProcessInstanceQuery().active().list().size(), CoreMatchers.is(1));
  }
}
