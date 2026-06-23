# Ethio Matric - Flutter Application Conversion

This project represents the complete, high-performance Flutter conversion of the native Kotlin **Ethio Matric National Exam Preparation Suite**. 

It replicates all 600+ curated and dynamic questions across **Mathematics, Biology, Aptitude, English, Physics, and Chemistry**, complete with:
- **Offline SQLite Databases** (via the standard `sqflite` plugin) to keep track of bookmarks and session history.
- **Provider State Management** for reactive state transitions and robust countdown timers.
- **Material 3 Visual Identity** matching the modern cosmic-slate theme, featuring dark widgets and accuracy rings.

---

## 🚀 How to Download & Run Locally

To get this app running on your machine, follow these steps:

### Phase 1: Download from Google AI Studio
1. In the top corner of the Google AI Studio interface, click on the **Export** or **Download as ZIP** menu option to get the entire project archive.
2. Extract the downloaded ZIP file to a convenient folder on your computer.

### Phase 2: Setup Flutter
If you don't have Flutter installed:
1. Download and install Flutter for your OS from the official website: [https://docs.flutter.dev/get-started/install](https://docs.flutter.dev/get-started/install).
2. Verify your installation by running in your terminal:
   ```bash
   flutter doctor
   ```

### Phase 3: Generate Native Wrappers & Run
Once Flutter is set up:
1. Open your terminal and change directories into the unpacked folder:
   ```bash
   cd path/to/ethio_matric
   ```
2. Generate fresh, customized platform wrapper directories (e.g., `android/`, `ios/`) targeted directly to your local installed SDK environment by running:
   ```bash
   flutter create --org com.aistudio.ethiomatric .
   ```
3. Fetch dependencies declared in `pubspec.yaml`:
   ```bash
   flutter pub get
   ```
4. Find your plug-in emulator, simulator, or connected device, and launch the application:
   ```bash
   flutter run
   ```

---

## 📦 How to Build the Production APK
To compile the final, highly optimized release App Package (APK), execute:

```bash
flutter build apk --release
```

This will output the compiled package to your terminal's path:
`build/app/outputs/flutter-apk/app-release.apk`
You can transfer this file directly to any Android device to install and practice fully offline!

---

## 🛠️ Codebase Architecture

Our converted Flutter project structures consist of:
*   `pubspec.yaml`: Core dependency declaration page.
*   `lib/main.dart`: Navigational router and central entry points.
*   `lib/models/question.dart`: Multi-subject question layout schema.
*   `lib/models/bookmark.dart`: Bookmark mapping schema serialization.
*   `lib/models/history.dart`: Historic logs serialization templates.
*   `lib/data/question_bank.dart`: Curated 600+ question bank generator with math/physics/chem formulae.
*   `lib/database/database_helper.dart`: Standard Sqlite services executing query insert, fetch, and purge actions.
*   `lib/providers/quiz_provider.dart`: Business engine hosting option selection, answers verification, dynamic timers, and database cache registers.
*   `lib/screens/dashboard_screen.dart`: Main dashboard displaying subject drills grids and review logs triggers.
*   `lib/screens/quiz_screen.dart`: Interactive active quiz desk featuring line indicators, progress navigators, highlights, checks, and step guides.
*   `lib/screens/result_screen.dart`: Comprehensive metric reviews, time indices, accuracy gauges, and expandable reviews.
*   `lib/screens/bookmarks_screen.dart`: Folders listing all saved bookmark objects with expand options and dynamic delete controls.
*   `lib/screens/history_screen.dart`: Logs displaying accuracy aggregate scores, aggregate counts, session entries, and purge operations.
