# Android micropost app

This repository is an example Android application, which is based on Rails tutorial app.

[![Build Status](https://travis-ci.org/springboot-angular2-tutorial/android-app.svg?branch=master)](https://travis-ci.org/springboot-angular2-tutorial/android-app)
[![Coverage Status](https://coveralls.io/repos/github/springboot-angular2-tutorial/android-app/badge.svg?branch=master)](https://coveralls.io/github/springboot-angular2-tutorial/android-app?branch=master)

Key components.

* Dagger 2
* Retrolambda
* RxJava / RxAndroid
* Retrofit 2
* Data Binding
* Robolectric 3

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
./gradlew testDebug
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
