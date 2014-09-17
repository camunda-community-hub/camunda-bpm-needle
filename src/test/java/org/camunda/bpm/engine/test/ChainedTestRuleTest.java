package org.camunda.bpm.engine.test;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class ChainedTestRuleTest {

  private String result = "";

  public final TestRule outerRule = new TestRule() {
    @Override
    public Statement apply(Statement base, Description description) {
      return new Statement() {
        @Override
        public void evaluate() throws Throwable {
          result += "foo";
        }
      };
    }
  };

  @Rule
  public final ChainedTestRule<TestRule, TestRule> chainedTestRule = ChainedTestRule.newChain(outerRule, new TestRule() {
    @Override
    public Statement apply(Statement base, Description description) {
      return new Statement() {
        @Override
        public void evaluate() throws Throwable {
          result += "bar";
        }
      };
    }
  });

  @Test
  public void should_execute_outer_and_inner_rule() {
    Assert.assertThat(result, CoreMatchers.is("foobar"));
  }
}
