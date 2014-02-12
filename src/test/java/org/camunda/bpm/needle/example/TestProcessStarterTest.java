package org.camunda.bpm.needle.example;

import com.google.common.collect.Maps;
import org.camunda.bpm.engine.RuntimeService;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.needle4j.annotation.Mock;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.junit.NeedleBuilders;
import org.needle4j.junit.NeedleRule;

import javax.inject.Inject;
import java.util.Map;

import static org.mockito.Mockito.verify;

public class TestProcessStarterTest {

  @Rule
  public final NeedleRule needleRule = NeedleBuilders.needleRule().build();

  @Spy
  @ObjectUnderTest(implementation = TestProcessStarterBean.class)
  private TestProcessStarter testProcessStarter;

  @Mock
  private RuntimeService runtimeService;

  @Test
  public void should_start_process_with_userId() {

    testProcessStarter.startProcessWithUser("foo");

    Map<String, Object> variables = Maps.newHashMap();
    variables.put(TestProcessStarter.VARIABLE_STARTED_BY, "foo");

    verify(runtimeService).startProcessInstanceByKey("test-process", variables);
    verify(testProcessStarter).startProcessWithUser("foo");

  }

}
