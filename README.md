# Authentication App - Luminor Test Task

## Description

A login and registration screen built with modern Android development practices. 
Users can register with email and password, log in, and stay logged in across app restarts. 
All user data is stored locally using Room database.

## Modules

### Authentication

The auth screen supports both login and registration with a single UI. 
Field validation is performed for email format and password length (minimum 6 characters). 
Error messages are displayed inline below each field, and a Snackbar is used for 
login/register result feedback (wrong password, user doesn't exist, etc.).

### Dashboard

Shown after successful login or registration. 
Displays the logged-in user's email and provides a logout button that clears the session 
and navigates back to the auth screen.

### Session

Session persistence is handled with Jetpack DataStore. 
The logged-in user's email is saved on login/register and cleared on logout. 
On app launch, if a session exists, the user is taken directly to the Dashboard.

### Storage

User credentials are stored in a local Room database. 
Passwords are hashed using SHA-256 before storage. 
The database stores user email (primary key) and password hash.

## Tech Stack

- **Kotlin** - Android's modern language
- **Jetpack Compose** - UI builder with Material 3
- **Navigation Compose** - for navigation between screens
- **MVVM** - View Model state management
- **Room** - database to store data locally on device
- **DataStore Preferences** - session persistence
- **KSP** - annotation processing for Room
- **Coroutines & Flow** - async operations

## Materials

- [Jetpack Compose documentation](https://developer.android.com/develop/ui/compose)
- [Material 3 design tokens](https://m3.material.io/)
- [Room persistence library](https://developer.android.com/training/data-storage/room)
- [DataStore guide](https://developer.android.com/topic/libraries/architecture/datastore)
- [Navigation Compose](https://developer.android.com/develop/ui/compose/navigation)
