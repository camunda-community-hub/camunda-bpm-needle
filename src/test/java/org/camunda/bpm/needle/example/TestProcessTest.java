package org.camunda.bpm.needle.example;

import java.util.UUID;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.assertions.ProcessEngineTests;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.extension.needle.ProcessEngineNeedleRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.needle4j.annotation.Mock;
import org.needle4j.annotation.ObjectUnderTest;

public class TestProcessTest {

  @Rule
  public final ProcessEngineNeedleRule processEngineNeedleRule = ProcessEngineNeedleRule.fluentNeedleRule(this).build();

  @ObjectUnderTest(implementation = TestProcessStarterBean.class)
  public TestProcessStarter testProcessStarter;

  @Mock
  private JavaDelegate serviceTaskMock;

  @Inject
  private TaskService taskService;

  @Inject
  private RuntimeService runtimeService;

  @Test
  @Deployment(resources = "test-process.bpmn")
  public void should_deploy_and_start_process_via_starter_bean() {
    Assert.assertNotNull(processEngineNeedleRule.getDeploymentId());

    Mocks.register("serviceTask", serviceTaskMock);

    final ProcessInstance processInstance = testProcessStarter.startProcessWithUser("foo", UUID.randomUUID().toString());

    ProcessEngineTests.assertThat(processInstance).isActive();

    ProcessEngineTests.assertThat(processInstance).task().isNotAssigned();

    final Task task = ProcessEngineTests.task();
    Assertions.assertThat(task).isNotNull();
    ProcessEngineTests.assertThat(task).hasDefinitionKey("task_wait");

    taskService.complete(task.getId());

    ProcessEngineTests.assertThat(processInstance).isEnded();
  }
}
