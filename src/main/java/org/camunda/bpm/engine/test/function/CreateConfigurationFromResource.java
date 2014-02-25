package org.camunda.bpm.engine.test.function;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.ProcessEngineDelegate;
import org.camunda.bpm.engine.test.cfg.MostUsefulProcessEngineConfiguration;

import javax.swing.text.html.Option;
import java.io.FileNotFoundException;

import static com.google.common.base.Optional.fromNullable;

/**
 * Creates a new ProcessEngineConfiguration based on camunda.cfg.xml. Falls back to activiti.cfg.xml for compatibility reasons.
 * If no cfg.xml files can be found, a MostUsefulProcessEngineConfiguration is created.
 *
 * @author Jan Galinski, Holisticon AG
 */
public enum CreateConfigurationFromResource implements Supplier<ProcessEngineConfiguration> {
  INSTANCE;


  /**
   *
   */
  private static class ConfigurationSupplier implements  Supplier<ProcessEngineConfiguration> {

    private final String cfgXmlFilename;

    private ConfigurationSupplier(String cfgXmlFilename) {
      this.cfgXmlFilename = cfgXmlFilename;
    }

    @Override
    public ProcessEngineConfiguration get() {
      try {
        return ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(cfgXmlFilename);
      } catch (Exception e) {
        return null;
      }
    }
  }


  private final Supplier<ProcessEngineConfiguration> camundaCfgXmlSupplier = new ConfigurationSupplier("camunda.cfg.xml");

  private final Supplier<ProcessEngineConfiguration> activitiCfgXmlSupplier = new ConfigurationSupplier("activiti.cfg.xml");

  @Override
  public ProcessEngineConfiguration get() {
    ProcessEngineConfiguration configuration = camundaCfgXmlSupplier.get();
    if (configuration == null) {
      configuration = activitiCfgXmlSupplier.get();
    }
    if (configuration == null) {
      configuration = MostUsefulProcessEngineConfiguration.SUPPLIER.get();
    }

    return configuration;
  }

  public ProcessEngine buildProcessEngine() {
    return get().buildProcessEngine();
  }

  public ProcessEngineDelegate createProcessEngineDelegate(boolean eager) {
    return new ProcessEngineDelegate(get(), eager);
  }
}
