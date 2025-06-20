# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is an Android Compose application that demonstrates visual differences between Material 2 and Material 3 SnackBar implementations. The app shows how custom SnackBars with centered content (Icon + Text) behave with different text lengths (1-4 lines) across Material Design versions.

## Architecture

- **MainActivity.kt**: Main entry point with navigation between Material 2/3 screens
- **Material2SnackBarScreen.kt**: Custom SnackBar implementation using `androidx.compose.material`
- **Material3SnackBarScreen.kt**: Custom SnackBar implementation using `androidx.compose.material3`
- Both implementations use identical custom SnackBar structure with centered Box, widthIn constraint (max 700.dp), and Column with Icon + Text

## Key Implementation Details

- Custom SnackBarHost implementations in both screens override default styling
- SnackBars are centered horizontally with padding and width constraints
- Both screens use identical test buttons for 1-4 line text variations
- Material 3 version uses `snackbarData.visuals.message` vs Material 2's `snackbarData.message`
- Material 3 version requires `@OptIn(ExperimentalMaterial3Api::class)`

## Build Commands

```bash
# Build the project
./gradlew build

# Install debug APK
./gradlew installDebug

# Run tests
./gradlew test
./gradlew connectedAndroidTest
```

## Dependencies

- Both Material 2 (`androidx.compose.material`) and Material 3 (`androidx.compose.material3`) are included
- Uses Compose BOM version 2024.09.00
- Target SDK 35, Min SDK 24
- Kotlin 2.0.21 with Compose compiler plugin