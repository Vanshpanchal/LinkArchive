# LinkArchive 
![icon](https://github.com/Vanshpanchal/LinkArchive/assets/83567205/cace622d-0ddc-469a-a93e-32e378b5e77f)

LinkArchive is an Android Kotlin app that allows users to store important URL resources in one place, thereby enhancing productivity. The app provides modules for signup, signin, logout, reset password, and utilizes Firebase Authentication with email and password. It also utilizes Firebase Firestore as a database to store the data.

## Features

- **Signup:** Users can create a new account by providing their email and password.
- **Signin:** Existing users can sign in using their registered email and password.
- **Logout:** Users can securely log out of their accounts.
- **Reset Password:** Users can reset their passwords if they have forgotten them.
- **URL Resource Management:** Users can store and manage important URL resources.
- **Firebase Authentication:** The app uses Firebase Authentication to handle user authentication securely.
- **Firebase Firestore:** The app utilizes Firebase Firestore as a cloud-based database to store and retrieve user data.

## Prerequisites

To use LinkArchive, make sure you have the following:

- Android device running Android 5.0 (Lollipop) or later.
- Android Studio installed on your development machine.
  
## Getting Started

Follow these steps to get started with LinkArchive:

1. Clone the repository to your local machine using Git or download it as a ZIP file and extract it.
2. Open the project in Android Studio.
3. Set up Firebase Authentication and Firestore:
   - Create a new project in the Firebase Console.
   - Enable Firebase Authentication with email and password.
   - Enable Firebase Firestore as your database.
   - Download the `google-services.json` file from the Firebase Console.
   - Place the `google-services.json` file in the `app` directory of the project.
4. Build and run the app on your Android device or emulator.

## Configuration

No additional configuration is required for LinkArchive.

## Dependencies

LinkArchive relies on the following dependencies:

- Firebase Authentication: Handles user authentication.
- Firebase Firestore: Provides cloud-based database functionality.

All necessary dependencies are included in the `build.gradle` files.

## Contributing

If you want to contribute to LinkArchive, follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Make your changes and test thoroughly.
4. Commit your changes with clear and descriptive commit messages.
5. Push your changes to your forked repository.
6. Create a pull request to the main repository.

## Issues

If you encounter any issues or have any suggestions, please open an issue on the GitHub repository.

## Acknowledgments

LinkArchive utilizes the following open-source libraries:

- [Firebase SDK](https://firebase.google.com/docs/android)
- [Firebase Android](https://firebase.google.com/docs/auth/android/manage-users)
- [Android Kotlin](https://developer.android.com/kotlin)
