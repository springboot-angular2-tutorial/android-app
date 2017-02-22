# Android micropost app

This repository is an example Android application based on Rails tutorial app.

[![Build Status](https://travis-ci.org/springboot-angular2-tutorial/android-app.svg?branch=master)](https://travis-ci.org/springboot-angular2-tutorial/android-app)

* Kotlin 100% without kapt
* [Kodein](https://github.com/SalomonBrys/Kodein) as Dependency Injection
* [RxJava](https://github.com/ReactiveX/RxJava) / [RxAndroid](https://github.com/ReactiveX/RxAndroid)
* [RxBinding](https://github.com/JakeWharton/RxBinding)
* [Retrofit](https://github.com/square/retrofit)
* [Robolectric](http://robolectric.org/) for testing model layer
* [Espresso](https://google.github.io/android-testing-support-library/docs/espresso/) for testing presentation layer

## Getting Started

Prepare backend app.

```
git clone https://github.com/springboot-angular2-tutorial/boot-app.git
cd boot-app
mvn spring-boot:run
```

Configure API URL.

```
cp gradle.properties.example gradle.properties
```

```
# gradle.properties
micropost.apiUrl="Your API HERE"
```

Then, just run from Android Studio.

Testing.

```
./gradlew testDebugUnitTest
./gradlew connectedAndroidTest
```

Release build.

```
cp yourkey app/myreleasekey.jks
export KSTOREPWD=yourpass
export KEYPWD=yourpass
./gradlew assembleRelease
```

## Tutorial

Under construction...

## License

[MIT](/LICENSE)
