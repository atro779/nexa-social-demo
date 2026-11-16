# Nexa Android

An offline-first Android demo app built with Kotlin and Android Views.

## Status

This repository contains an early MVP source project. It is not a production-ready social or dating application.

### Implemented
- Guest onboarding and local profile preferences
- Fictional demo profiles
- Local demo conversations persisted with SharedPreferences
- Demo-only wallet and VIP screens (no payments)
- Settings and local data reset

### Not implemented
- Real authentication or backend
- SQLite/Room database
- Real camera preview, microphone capture, or WebRTC calls
- Real payments, wallet transfers, or subscriptions
- Production security, privacy, and localization review

## Build

Requirements: JDK 17, Android SDK, and Gradle 8.x.

```bash
gradle :app:assembleDebug
```

APK output: `app/build/outputs/apk/debug/app-debug.apk`.

See [BUILD_INSTRUCTIONS.md](BUILD_INSTRUCTIONS.md), [ARCHITECTURE.md](ARCHITECTURE.md), [DATABASE_SCHEMA.md](DATABASE_SCHEMA.md), and [TEST_REPORT.md](TEST_REPORT.md).

## Package
`com.nexa.social`

Minimum Android version: API 26 (Android 8.0).
