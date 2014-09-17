package org.camunda.bpm.needle.example;

import static org.camunda.bpm.needle.example.TestProcessStarterBean.variablesStartedByUser;
import static org.mockito.Mockito.verify;
import static org.needle4j.junit.NeedleBuilders.needleRule;

import java.util.UUID;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.Rule;
import org.junit.Test;
import org.needle4j.annotation.Mock;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.junit.NeedleRule;

public class TestProcessStarterTest {

  public static final String USER_ID = "foo";

  @Rule
  public final NeedleRule needleRule = needleRule().build();

  @ObjectUnderTest(implementation = TestProcessStarterBean.class)
  private TestProcessStarter testProcessStarter;

  @Mock
  private RuntimeService runtimeService;

  @Test
  public void should_start_process_with_userId() {
    final String businessKey = UUID.randomUUID().toString();

    testProcessStarter.startProcessWithUser(USER_ID, businessKey);

    verify(runtimeService).startProcessInstanceByKey(TestProcessStarterBean.PROCESS_KEY, businessKey, variablesStartedByUser(USER_ID));
  }

}
