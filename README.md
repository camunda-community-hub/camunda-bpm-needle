# camunda-bpm-needle

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.camunda.bpm.extension/camunda-bpm-needle/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.camunda.bpm.extension/camunda-bpm-needle)

[Needle4j](http://www.needle4j.org) (f.k.a. needle) is a framework to ease DI testing. It allows writing very effective, boilerplate free Junit or TestNG test by providing mocks for every Injection point.

![needle4j logo](https://github-camo.global.ssl.fastly.net/7141f70aa5224cbd50220ef265decf595091955c/687474703a2f2f6e6565646c652e73707265652e64652f7075626c69632f696d616765732f6e6565646c652d636f66666565637570732d33383070782e6a7067)

This is how a test written with needle looks like. Check out the source code for the full [example](https://github.com/camunda/camunda-bpm-needle/tree/master/src/test/java/org/camunda/bpm/needle/example).

```java 
 public static final String USER_ID = "foo";

  @Rule
  public final NeedleRule needleRule = needleRule().build();

  @ObjectUnderTest(implementation = TestProcessStarterBean.class)
  private TestProcessStarter testProcessStarter;

  @Mock
  private RuntimeService runtimeService;

  @Test
  public void should_start_process_with_userId() {
    final String businessKey = UUID.randomUUID().toString();

    testProcessStarter.startProcessWithUser(USER_ID, businessKey);

    verify(runtimeService).startProcessInstanceByKey(TestProcessStarterBean.PROCESS_KEY,
            businessKey, variablesStartedByUser(USER_ID));
  }
```

Now, this is the bean test. Now you want to write the process test. Since you have special requirements how the process is started and you already tested the behavior, instead of rewriting the process start manually, why not reuse the process starter.

This is where camunda-bpm-needle gets useful. Instead of using mocks (like in the previous sample), the ProcessEngineNeedleRule can inject the in memory process engine services into your test case and use your beans.
And since you are still working with the in memory engine, you can easily use mocks for the parts of the process you do not want (or need) to test here.

```java

  @Rule
  public final ProcessEngineNeedleRule processEngineNeedleRule = ProcessEngineNeedleRule.fluentNeedleRule(this).build();

  @ObjectUnderTest(implementation = TestProcessStarterBean.class)
  public TestProcessStarter testProcessStarter;

  @Mock
  private JavaDelegate serviceTaskMock;

  @Inject
  private TaskService taskService;

  @Inject
  private RuntimeService runtimeService;

  @Test
  @Deployment(resources = "test-process.bpmn")
  public void should_deploy_and_start_process_via_starter_bean() {
    Mocks.register("serviceTask", serviceTaskMock);

    final ProcessInstance processInstance = testProcessStarter.startProcessWithUser("foo", UUID.randomUUID().toString());

    Assert.assertNotNull(processEngineNeedleRule.getDeploymentId());

    Task task = taskService.createTaskQuery().active().singleResult();
    Assert.assertNotNull(task);

    taskService.complete(task.getId());

    Assert.assertNull(runtimeService.createProcessInstanceQuery().active().singleResult());
  }
  
```

## Get started

Just include camunda-bpm-needle in the test scope of your project:

```xml
<dependency>
  <groupId>org.camunda.bpm.extension</groupId>
  <artifactId>camunda-bpm-needle</artifactId>
  <scope>test</scope>
  <version>1.1</version>
</dependency>
```

* [1.1 on camunda nexus](https://app.camunda.com/nexus/content/repositories/camunda-bpm-community-extensions/org/camunda/bpm/extension/camunda-bpm-needle/1.1/)
* [1.1 on maven central](http://repo1.maven.org/maven2/org/camunda/bpm/extension/camunda-bpm-needle/1.1/)

and start using NeedleRule or ProcessEngineNeedleRule in your tests. Have a look at the examples in (src/test/java). 


## Resources

* [Issue Tracker](https://github.com/camunda/camunda-bpm-needle/issues)
* [Contributing](https://github.com/camunda/camunda-bpm-needle/blob/master/CONTRIBUTING.md) 


## Maintainer

* [Jan Galinski](https://github.com/jangalinski), [Holisticon AG](http://www.holisticon.de)
* [Simon Zambrovski](https://github.com/zambrovski), [Holisticon AG](http://www.holisticon.de)


## License

Apache License, Version 2.0

