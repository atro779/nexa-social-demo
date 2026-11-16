# Build instructions
1. Install Android Studio and JDK 17.
2. Install Android SDK Platform 35 and Build Tools through SDK Manager.
3. Open this project folder in Android Studio.
4. Sync Gradle.
5. Run `gradle :app:assembleDebug` or use Android Studio > Build > Build APK(s).
6. Debug APK output: `app/build/outputs/apk/debug/app-debug.apk`.

A release APK requires signing configuration. Do not distribute an unsigned release artifact.
