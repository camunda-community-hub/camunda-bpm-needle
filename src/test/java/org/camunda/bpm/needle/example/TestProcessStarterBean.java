package org.camunda.bpm.needle.example;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author Jan Galinski, Holisticon AG
 */
@Named
public class TestProcessStarterBean implements Serializable, TestProcessStarter {

  public static Map<String, Object> variablesStartedByUser(String startedByUser) {
    Preconditions.checkArgument(StringUtils.isNotBlank(startedByUser));
    final Map<String, Object> variables = Maps.newHashMap();
    variables.put(VARIABLE_STARTED_BY, startedByUser);
    return variables;
  }

  public static final String PROCESS_KEY = "test-process";

  @Inject
  private RuntimeService runtimeService;

  @Override
  public ProcessInstance startProcessWithUser(String startedByUser, String businessKey) {
    return runtimeService.startProcessInstanceByKey(PROCESS_KEY, businessKey, variablesStartedByUser(startedByUser));
  }

}
