package org.camunda.bpm.engine.test;

import java.util.Date;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineServices;
import org.junit.rules.TestRule;

/**
 * Combined interface of {@link org.camunda.bpm.engine.ProcessEngineServices}
 * and {@link TestRule}.
 */
public interface ProcessEngineTestRule extends TestRule, ProcessEngineServices {

  /**
   * Sets current time of in memory engine. Use to test timers etc.
   *
   * @param currentTime time to set
   */
  void setCurrentTime(Date currentTime);

  /**
   * Provide deployment id after deploying with @Deployment-annotation.
   *
   * @return current deployment id
   */
  String getDeploymentId();

  /**
   * Get the process engine.
   *
   * @return the process engine
   */
  ProcessEngine getProcessEngine();

}
