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
 * Created by jangalinski on 02.02.14.
 *
 * @author Jan Galinski, Holisticon AG
 */
public enum CreateConfigurationFromResource implements Supplier<ProcessEngineConfiguration> {
  INSTANCE;

  public static String DEFAULT_CFG_XML_NAME = "camunda.cfg.xml";
  public static String COMPAT_CFG_XML_NAME = "activiti.cfg.xml";


  private final Supplier<ProcessEngineConfiguration> camundaCfgXmlSupplier = new Supplier<ProcessEngineConfiguration>() {
    @Override
    public ProcessEngineConfiguration get() {
      try {
        return ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(DEFAULT_CFG_XML_NAME);
      } catch (Exception e) {
        return null;
      }
    }
  };

  private final Supplier<ProcessEngineConfiguration> activitiCfgXmlSupplier = new Supplier<ProcessEngineConfiguration>() {
    @Override
    public ProcessEngineConfiguration get() {
      try {
        return ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(COMPAT_CFG_XML_NAME);
      } catch (Exception e) {
        return null;
      }
    }
  };

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
