package org.camunda.bpm.engine.test.needle;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Arrays;
import java.util.Set;

import org.apache.commons.lang3.builder.Builder;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.test.function.CreateConfigurationFromResource;
import org.needle4j.injection.InjectionProvider;

import com.google.common.collect.Sets;

/**
 * Builder to create and configure instances of {@link ProcessEngineNeedleRule}.
 */
public class ProcessEngineNeedleRuleBuilder implements Builder<ProcessEngineNeedleRule> {

  private final Object testInstance;
  private ProcessEngineConfiguration configuration = CreateConfigurationFromResource.INSTANCE.get();
  private final Set<InjectionProvider<?>> additionalInjectionProviders = Sets.newHashSet();

  public ProcessEngineNeedleRuleBuilder(final Object testInstance) {
    this.testInstance = testInstance;
  }

  public ProcessEngineNeedleRuleBuilder withConfiguration(final ProcessEngineConfiguration configuration) {
    checkArgument(configuration != null, "configuration must not be null!");
    this.configuration = configuration;
    return this;
  }

  public ProcessEngineNeedleRuleBuilder withConfiguration(final String configurationFile) {
    return withConfiguration(ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(configurationFile));
  }

  public ProcessEngineNeedleRuleBuilder addInjectionProvider(final InjectionProvider<?>... additionalInjectionProviders) {
    this.additionalInjectionProviders.addAll(Arrays.asList(additionalInjectionProviders));
    return this;
  }

  @Override
  public ProcessEngineNeedleRule build() {
    return new ProcessEngineNeedleRule(testInstance, configuration, additionalInjectionProviders);
  }

}
