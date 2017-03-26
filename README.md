# BlueprintPong
This is a Pong clone written using libGDX

## Screenshot
![screenshot](./screenshot.png)

## Getting Started

### Desktop

1. Clone the project
2. Navigate to the project directory in your terminal/command prompt
3. If you are running on a Unix-like platform such as Linux or Mac OS X, run:

    ```
    ./gradlew desktop:dist
    ```
   If you are running on Windows, run:

    ```
    gradlew desktop:dist
    ```
    This will create a runnable JAR file located in the `desktop/build/libs/` folder
4. Go to `desktop/build/libs/` and run `java -jar desktop-1.0.jar`

### Android

1. Clone the project
2. Navigate to the project directory in your terminal/command prompt
3. If you are running on a Unix-like platform such as Linux or Mac OS X, run:

    ```
    ./gradlew android:assembleRelease
    ```
   If you are running on Windows, run:

    ```
    gradlew android:assembleRelease
    ```
    This will create an unsigned APK file in the `android/build/outputs/apk` folder. Before you can install or publish this APK, you must [sign it](https://developer.android.com/studio/publish/app-signing.html). The APK build by the above command is already in release mode, you only need to follow the steps for keytool and jarsigner. You can install this APK file on any Android device that allows [installation from unknown sources](https://developer.android.com/distribute/tools/open-distribution.html#unknown-sources).

## Instructions
You are the paddle on the left. On desktop, use arrow keys to move the paddle or click where you want the paddle to move to. On Android devices, touch the screen where you want the paddle to move to. First player to 11 wins.

## Useful Links
Resources useful for the completion of this project:

* [libGDX](https://libgdx.badlogicgames.com/) (cross-platform game development framework)
* [Gradle](https://gradle.org) (for building and dependency management)
* [Font](http://www.fontspace.com/kc-fonts/bluprint) (the font used in the game is Bluprint font created by KC Fonts)
