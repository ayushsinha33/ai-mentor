# AI Mentor — Adaptive Interview Coach

An Android starter app for an adaptive interview coach covering Java, Spring Boot,
DSA, System Design, Kafka, Redis, databases, Kubernetes, AI/ML and more.

## Build locally
Open in Android Studio and run the `app` configuration.

## Build with GitHub Actions
Push this repository to GitHub. The workflow in `.github/workflows/build-apk.yml`
builds a debug APK and uploads it as an Actions artifact.

## Important
This starter includes the coaching engine and UI. To make the coach use a real
LLM, configure the backend/API layer rather than embedding a secret API key in
the Android APK.
