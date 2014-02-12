package org.camunda.bpm.engine.test;

import com.google.common.base.Supplier;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Created by jangalinski on 03.02.14.
 *
 * @author Jan Galinski, Holisticon AG
 */
public abstract class ChainedTestRule<O extends TestRule, I extends TestRule> implements TestRule {

  public static ChainedTestRule<TestRule,TestRule> newChain(final TestRule outerRule, final TestRule innerRule) {
    return new ChainedTestRule<TestRule,TestRule>(outerRule) {

      @Override
      protected TestRule innerRule() {
        return innerRule;
        };
    };
  }

  protected O outerRule;


  public ChainedTestRule(final O outerRule) {
    this.outerRule = outerRule;
  }

  protected abstract I innerRule();


  @Override
  public Statement apply(Statement base, Description description) {
    return RuleChain.outerRule(outerRule).around(innerRule()).apply(base,description);
  }
}
