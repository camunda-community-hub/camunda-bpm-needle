package org.camunda.bpm.extension.needle;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions;
import org.camunda.bpm.engine.test.assertions.ProcessEngineTests;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.inject.Inject;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.claim;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.task;
import static org.camunda.bpm.extension.needle.ProcessEngineNeedleRule.fluentNeedleRule;

public class NeedleRuleWithAssertionsTest {

  static {
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();
  }

  @Rule
  public final ProcessEngineNeedleRule processEngineNeedleRule = fluentNeedleRule(this).build();

  @Inject
  private RuntimeService runtimeService;

  @Inject
  private TaskService taskService;

  @Test
  @Deployment(resources = "test-process.bpmn")
  public void waits_in_user_task_after_start() {
    ProcessInstance instance = runtimeService.startProcessInstanceByKey("test-process");

    assertThat(instance).isStarted().isWaitingAt("task_wait").task();

    claim(task(), "foo");
    assertThat(instance).task().isAssignedTo("foo");
  }
}