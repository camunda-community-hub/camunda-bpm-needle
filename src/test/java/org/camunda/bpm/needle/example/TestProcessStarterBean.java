package org.camunda.bpm.needle.example;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by jangalinski on 04.02.14.
 *
 * @author Jan Galinski, Holisticon AG
 */
@Named
public class TestProcessStarterBean implements Serializable, TestProcessStarter{

  @Inject
  private RuntimeService runtimeService;

  @Override
  public ProcessInstance startProcessWithUser(String startedByUser) {
    return runtimeService.startProcessInstanceByKey("test-process", variablesStartedByUser(startedByUser));
  }

  private Map<String,Object> variablesStartedByUser(String startedByUser) {
    Preconditions.checkArgument(StringUtils.isNotBlank(startedByUser));
    Map<String, Object> variables = Maps.newHashMap();
    variables.put(VARIABLE_STARTED_BY,startedByUser);
    return variables;
  }
}
