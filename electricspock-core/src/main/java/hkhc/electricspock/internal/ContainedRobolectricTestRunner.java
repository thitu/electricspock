package hkhc.electricspock.internal;

import java.lang.reflect.Method;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.internal.AndroidSandbox;
import org.robolectric.internal.bytecode.InstrumentationConfiguration;

/**
 * Modified RobolectricTestRunner solely to be used by Spock interceptor.
 */
public class ContainedRobolectricTestRunner extends RobolectricTestRunner {

  private FrameworkMethod placeholderMethod = null;
  private Method bootstrappedMethod = null;

  /**
   * Pretend to be a test runner for the placeholder test class. We don't actually run that test method. Just use it to trigger
   * all initialization of Robolectric infrastructure, and use it to run Spock specification.
   */
  public ContainedRobolectricTestRunner() throws InitializationError {
    super(PlaceholderTest.class);
  }

  public ContainedRobolectricTestRunner(Class<?> clazz) throws InitializationError {
    super(PlaceholderTest.class);
  }

  FrameworkMethod getPlaceHolderMethod() {
    if (placeholderMethod == null) {
      placeholderMethod = getChildren().get(0);
    }

    return placeholderMethod;
  }

  @Override
  protected List<FrameworkMethod> getChildren() {
    return super.getChildren();
  }

  private Method getBootstrappedMethod() {
    if (bootstrappedMethod == null) {
      bootstrappedMethod = createBootstrappedMethod();
    }

    return bootstrappedMethod;
  }

  private Method getMethod(Class<?> clazz, String methodName) {
    try {
      return clazz.getMethod(methodName);
    }
    catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  private Method createBootstrappedMethod() {
    FrameworkMethod placeholderMethod = getPlaceHolderMethod();
    AndroidSandbox sdkEnvironment = getContainedSdkEnvironment();

    // getTestClass().getJavaClass() should always be PlaceholderTest.class, load under Robolectric's class loader
    Class<?> bootstrappedTestClass = sdkEnvironment.bootstrappedClass(getTestClass().getJavaClass());
    return getMethod(bootstrappedTestClass, placeholderMethod.getMethod().getName());
  }

  /**
   * Override to add itself to doNotAcquireClass, so as to avoid classloader conflict
   */
  @Override
  @NotNull
  protected InstrumentationConfiguration createClassLoaderConfig(final FrameworkMethod method) {
    return new InstrumentationConfiguration.Builder(super.createClassLoaderConfig(method))
      .doNotAcquireClass(getClass())
      .build();
  }

  public AndroidSandbox getContainedSdkEnvironment() {
    FrameworkMethod placeHolderMethod = getPlaceHolderMethod();
    AndroidSandbox androidSandbox = getSandbox(placeHolderMethod);
    // this loads in our shadows and configures our env.
    configureSandbox(androidSandbox, placeHolderMethod);
    return androidSandbox;
  }

  public void containedBeforeTest() throws Throwable {
    super.beforeTest(getContainedSdkEnvironment(), getPlaceHolderMethod(), getBootstrappedMethod());
  }

  public void containedAfterTest() {
    super.afterTest(getPlaceHolderMethod(), getBootstrappedMethod());
  }

  /**
   * A place holder test class to obtain a proper FrameworkMethod (which is actually a RoboFrameworkTestMethod) by reusing
   * existing code in RobolectricTestRunner
   */
  public static class PlaceholderTest {

    /* Just a placeholder, the actual content of the test method is not important */
    @Test
    public void testPlaceholder() {
    }
  }
}