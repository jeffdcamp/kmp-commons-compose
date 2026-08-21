# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [2.5.0] - 2026-08-21

### Added
- Added grouped/segmented settings API: `Setting.Group` with a `SegmentedScope` receiver providing `GroupedClickable`, `GroupedSwitch`, `GroupedSlider`, `GroupedClickableWithExpansion`, `GroupedSwitchWithExpansion`, `GroupedSwitchWithAction`, and `GroupedSwitchWithTwoChips`, using Material 3 Expressive `SegmentedListItem` shaping driven by a new `GroupPositionType` (`ONLY`/`FIRST`/`MIDDLE`/`LAST`)
- Added `Setting.SwitchWithAction` — a clickable row with a divided trailing toggle for a separate action
- Added `SettingsUiUtil` with `topAppBarColors()` and `scaffoldContainerColor()` helpers for Android System Settings-style contrast
- Added `Icons.AutoMirrored.Rounded.ArrowForwardIos` icon
- Added accessibility semantics across settings (heading roles, toggleable state, and slider `ProgressBarRangeInfo`/`setProgress`)
- Enabled Apple (iOS/macOS) klib cross-compilation from non-Mac hosts for publishing from Linux CI (`kotlin.native.enableKlibsCrossCompilation`, `kotlin.native.ignoreDisabledTargets`); Apple frameworks are only declared on macOS hosts
- Added `foojay-resolver-convention` plugin and `gradle/gradle-daemon-jvm.properties` for Gradle daemon JVM toolchain provisioning

### Changed
- Settings composables now expose `enabled` and `modifier` parameters and are split into value/flow overloads; `Slider` gained a customizable `valueText`
- `Setting.Header` now applies heading semantics and accepts a `modifier`
- Raised Android `minSdk` from 23 to 24
- Renamed the `assertK` version and library alias to `assertk`
- Switched the versions plugin id from `com.github.ben-manes.versions` to `io.github.ben-manes.versions`
- Moved Detekt and Kover configuration from the root build into the module build, and enabled the Kover verify rule
- Migrated the Android target from the `androidLibrary {}` DSL to `android {}` with a host-side unit test builder
- Updated dependencies: AGP 9.3.1, Kotlin 2.4.10, Ktor 3.5.2, Lifecycle 2.11.0, Material3 1.12.0-alpha03, detekt 2.0.0-alpha.6, kover 0.9.9, versions plugin 0.61.0, Gradle 9.7.0

### Fixed
- Fixed `Float.round` to use `10^decimalPlaces` instead of `10 * decimalPlaces` for the rounding multiplier

## [2.4.0] - 2026-06-24

### Changed
- Updated dependencies: AGP 9.2.1, Kotlin 2.4.0, Compose 1.11.1, Material3 1.12.0-alpha02, Ktor 3.5.0, Gradle 9.6.0

### Fixed
- Fixed partial path matching in `RouteMatcher` so paths only match exactly or at proper path boundaries (`/` or `?`), preventing false positives on partial prefixes

## [2.3.0] - 2026-05-01

### Added
- Added `inclusive` parameter to `popBackStack` and related navigation functions

### Changed
- Renamed `supportingText` to `text` in all dialog composables and UiState classes
- Renamed `DropDownMenuDialog` to `DropdownMenuDialog` (file, functions, and `DropDownMenuDialogUiState` → `DropdownMenuDialogUiState`)
- Alphabetized `when` branches in `LibraryDialogs`
- Reordered `MultiSelectDialog` and `MultiSelectDialogUiState` parameters (title/text before allItems/selectedItems)
- Updated `DropdownMenuDialog` to use `ExposedDropdownMenuAnchorType.PrimaryNotEditable`
- Added `properties` to `InputDialogUiState`, `TwoInputDialogUiState`, `DropdownMenuDialogUiState`, `MenuOptionsDialogUiState`, `MultiSelectDialogUiState`, and `RadioDialogUiState`
- Added `optionToSupportingText` parameter to `DropdownMenuDialog` and `DropdownMenuDialogUiState`
- Updated dependencies: AGP 9.2.0, Kotlin 2.3.21, Compose 1.11.0-beta03, Gradle 9.5.0
- Updated Android compileSdk to 37
- Replaced deprecated `LocalConfiguration` usage in `MultiSelectDialog`

### Removed
- Removed `ViewModelNavigation3Bar`
- Removed `iosX64` target

### Fixed
- Fixed `TwoInputDialog` UiState passing `minLengthSecond`/`maxLengthSecond` for the first input field instead of `minLengthFirst`/`maxLengthFirst`

## [2.2.0] - 2026-02-17

### Added
- Added deep link routing infrastructure for Navigation3

### Changed
- Refactored form fields to include stateless overloads
- Refactor Navigation3Navigator to support backstack change listeners
- Updated dependencies: AGP 9.0.1, Kotlin 2.3.0, Detekt 2.0.0-alpha.2, Compose 1.10.0


## [2.1.3] - 2025-11-26

### Added
- Added getCurrentBackStack() to Navigation3Navigator

### Changed
- Changed ResultStore to be an object (remove requirement of Dagger)

## [2.1.2] - 2025-11-24

### Changed
- Added decorators to NavigationState.toEntries()

## [2.1.1] - 2025-11-24

### Changed
- Improved Javadoc for DefaultNavigator and TopLevelBackStackNavigator

## [2.1.0] - 2025-11-23

### Changed
- Updated JetBrains compose to 1.10.0-beta02
- Improved support for Navigation3 Multiple Back Stacks
- Changed navigation3 generic from <T : NavKey> to <NavKey>

## [2.0.0] - 2025-11-16

### Added
- Added support for Navigation3

### Changed
- Updated JetBrains compose to 1.10.0-beta01
- Kotlin 2.2.21
- Added support for and Kotlin DateTime API (kotlinx.datetime to kotlin.time)
- Other version updates and build improvements
- Changed publishing to use Vanniktech Plugin

### Removed
- Removed support for Navigation2

## [1.0.0] - 2025-05-11

### Changed
- Updated compose plugin to 1.8.1 and applied fixes for changes to this dependency
- Android Gradle Library Plugin to "com.android.kotlin.multiplatform.library"
- Added support for only signing on platforms that can support certain build
- Other version updates

## [0.1.0] - 2025-04-10

### Changed
- Kotlin 2.1.20
- JetBrains compose plugin 1.8.0-beta02
- JetBrains navigation 2.9.0-alpha15
- Improved support for long text in MessageDialog
- Improved support for multiple lines in DayNightTextField and InputDialog
- Improved Navigation Action to support direct calls to the NavController
- Other version updates 

## [0.0.1] - 2024-12-18

### Added
- Initial commit

