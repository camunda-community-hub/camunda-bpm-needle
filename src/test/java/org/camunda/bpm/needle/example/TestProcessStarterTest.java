package org.camunda.bpm.needle.example;

import com.google.common.collect.Maps;
import de.akquinet.jbosscc.needle.annotation.Mock;
import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest;
import de.akquinet.jbosscc.needle.junit.NeedleRule;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.needle.ProcessEngineNeedleRule;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Map;

/**
 * Created by jangalinski on 04.02.14.
 *
 * @author Jan Galinski, Holisticon AG
 */
public class TestProcessStarterTest {

  @Rule
  public final NeedleRule needleRule = new NeedleRule();

  @ObjectUnderTest(implementation = TestProcessStarterBean.class)
  private TestProcessStarter testProcessStarter;

  @Mock
  private RuntimeService runtimeService;

  @Test
  public void should_start_process_with_userId() {

    testProcessStarter.startProcessWithUser("foo");
    Map<String,Object> variables = Maps.newHashMap();
    variables.put(TestProcessStarter.VARIABLE_STARTED_BY, "foo");

    Mockito.verify(runtimeService).startProcessInstanceByKey("test-process", variables);

  }

}
