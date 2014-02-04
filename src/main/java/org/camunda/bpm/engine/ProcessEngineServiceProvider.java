package org.camunda.bpm.engine;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;

/**
 * Defines all Services a ProcessEngine must provide. Use this to
 * separate the aspects "providing process engine services" and
 * "being a process engine".
 *
 * @author Jan Galinski, Holisticon AG
 */
public interface ProcessEngineServiceProvider  {

  RepositoryService getRepositoryService();

  RuntimeService getRuntimeService();

  FormService getFormService();

  TaskService getTaskService();

  HistoryService getHistoryService();

  IdentityService getIdentityService();

  ManagementService getManagementService();

  AuthorizationService getAuthorizationService();

}
