# ElectricSpock

[ ![Download](https://api.bintray.com/packages/thitu/releases/electricspock/images/download.svg) ](https://bintray.com/thitu/releases/electricspock/_latestVersion)

The latest version of this fork is `1.0.2` which has been updated to support [Robolectric](http://robolectric.org) version `4.0.2`

The original fork is [here](https://github.com/hkhc/electricspock)


## About

The Android test framework [Robolectric](https://github.com/robolectric/robolectric) is designed with JUnit in mind and it does not work well with Spock framework. Fortunately there is a project [RoboSpock](https://github.com/robospock/RoboSpock) is designed for that. It configure Robolectric properly under the framework of Spock framework. However it based on some internal source code of Robolectric and it has no official support to Robolectric 3.1 yet. There is an [issue](https://github.com/robospock/RoboSpock/issues/59) for that, but it has been quite some time. So I decided to make my own.

It is heavily based on RoboSpock project. It borrow a lot of code from there, and make some tweak of my own. This project is never possible without the excellent foundation.

Current version (0.5) of the library is tested with Robolectric 3.3.2.

# Installation (Gradle)

The archive of this project is deployed with [jitpack](https://jitpack.io). Add the following to your gradle build script.

Add it in your root build.gradle at the end of repositories:

```groovy
	allprojects {
		repositories {
			...
			maven { url  "https://dl.bintray.com/thitu/releases" }
		}
	}
```

Add the dependency

```groovy
	dependencies {
    ...
    testImplementation "com.github.thitu.electricspock:electricspock:1.0.2"
	}
```

Spock, Robolectric and Groovy are dependencies of ElectricSpock,
so it should work without adding these dependencies to build.gradle,
but you may override them with the version you prefer.

Then we may just write Spock specification with `ElectricSpecification`
class and Robolectric's `@Config` annotation

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
