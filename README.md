# Android micropost app

This repository is an example Android application, which is based on Rails tutorial app.

[![Build Status][travis-image]][travis-url]

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
vi app/src/debug/java/com/hana053/micropost/Constants.java
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

[travis-url]: https://travis-ci.org/springboot-angular2-tutorial/android-app
[travis-image]: https://travis-ci.org/springboot-angular2-tutorial/android-app.svg