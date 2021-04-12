# ElectricSpock (forked)

![Maven metadata URL](https://img.shields.io/maven-metadata/v?label=artifactory&metadataUrl=https%3A%2F%2Fthitu.jfrog.io%2Fartifactory%2Freleases%2Fcom%2Fgithub%2Fthitu%2Felectricspock%2Felectricspock%2Fmaven-metadata.xml)
[![Build Status](https://travis-ci.com/thitu/electricspock.svg?branch=master)](https://travis-ci.com/thitu/electricspock)

The latest version of this fork is `4.3.1-6` which has been updated to support [Robolectric](http://robolectric.org) version `4.3.1`

The original code that this is forked from is available [here](https://github.com/hkhc/electricspock).


# Installation via Gradle

Add the release repository to your top level `build.gradle` file:

```groovy
allprojects {
  repositories {
    maven { url  "https://thitu.jfrog.io/artifactory/releases" }
  }
}
```

Add the dependency to your project module's `build.gradle` file:

```groovy
dependencies {
  testImplementation "com.github.thitu.electricspock:electricspock:4.3.1-6"
}
```

Spock, Robolectric and Groovy are dependencies of ElectricSpock, so it should work without adding these dependencies to build.gradle, but you may override them with the version you prefer.

Then we may just write Spock specification with `ElectricSpecification` class and Robolectric's `@Config` annotation

```groovy

@Config(constants=BuildConfig)
class MySpec extends ElectricSpecification {

  def "Robolectric is enabled"() {

    when: "invoking call to Android API"
      android.util.Log.d("TAG", "Hello world")

    then: "there should not be any error"
      notThrown Exception
    }
  }
}

```

# ElectricSuite

The class `ElectricSuite` is a helper class to help organize test methods of Specification into group.
It is essentially a test class with `Suite` runner. However we don't need to use `@SuiteClasses` annotation
to specify test classes. Instead the class scan all static inner class as test classes.

For example

```groovy

class MySpec extends ElectricSuite {

  static class MyInnerSpec1 extends ElectricSpecification {
    [....]
  }

  static class MyInnerSpec2 extends ElectricSpecification [
    [....]
  }
}

```
