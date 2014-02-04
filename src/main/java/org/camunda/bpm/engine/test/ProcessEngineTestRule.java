package org.camunda.bpm.engine.test;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineServiceProvider;
import org.junit.rules.TestRule;

import java.util.Date;

/**
 * Combined interface of {@link org.camunda.bpm.engine.ProcessEngineServiceProvider}
 * and {@link TestRule}.
 */
public interface ProcessEngineTestRule extends TestRule, ProcessEngineServiceProvider {

  /**
   * Sets current toime of in memory engine. Use to test timers etc.
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
