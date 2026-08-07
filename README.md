# LeaveFlow – Firebase Leave Management System

LeaveFlow is an offline-first Android application built with Kotlin, Jetpack Compose, Room, Firebase, Hilt, and WorkManager.

## Firebase setup

1. Create a Firebase project and register an Android app with package name `com.leaveflow.app`.
2. Download `google-services.json` and place it at `app/google-services.json`.
3. In Firebase Authentication, enable **Email/Password**.
4. Create the default Cloud Firestore database and a Cloud Storage bucket.
5. Install the Firebase CLI, sign in, and select the project:

   ```powershell
   npm install -g firebase-tools
   firebase login
   firebase use --add
   ```

6. Deploy the database and storage rules:

   ```powershell
   firebase deploy --only firestore:rules,firestore:indexes,storage
   ```

The Android build remains compilable before `google-services.json` is supplied, but login clearly reports that Firebase is not configured and no cloud operation will run.

## Seed demo accounts and data

The seed tool uses Firebase Admin credentials and creates Auth accounts with stable UIDs matching the bundled Room demo records.

1. Create a Firebase service account key or configure Application Default Credentials. Never commit the key.
2. Set the credential path and run the seed:

   ```powershell
   $env:GOOGLE_APPLICATION_CREDENTIALS='C:\secure\firebase-service-account.json'
   cd firebase
   npm install
   npm run seed
   ```

Set `LEAVEFLOW_DEMO_PASSWORD` before running if you do not want the default `Pass@1234` password.

| Role | Email | Default password |
|---|---|---|
| Employee | john.doe@leaveflow.com | Pass@1234 |
| Employee | alice.perera@leaveflow.com | Pass@1234 |
| Manager | sarah.smith@leaveflow.com | Pass@1234 |
| HR | admin.hr@leaveflow.com | Pass@1234 |

## Run the Android app

1. Open this directory in Android Studio.
2. Wait for Gradle synchronization.
3. Run on an API 24+ emulator or physical device with Google Play services.

Command-line build:

```powershell
.\gradlew.bat :app:assembleDebug
```

## Data architecture

```text
Compose UI
   ↕ Flow
Room (local source of truth)
   ↕ transactional outbox/reconciliation
WorkManager + Firestore listeners
   ↕
Firebase Auth + Cloud Firestore + Cloud Storage
```

- Firebase Authentication owns passwords and persisted authentication state.
- Firestore stores user profiles, leave requests, and leave balances.
- Storage stores captured evidence under `leave-attachments/{employeeUid}/{requestId}/evidence.jpg`.
- Room drives the UI and keeps the app usable while offline.
- Local leave, balance, and sync-queue changes commit in one Room transaction.
- WorkManager immediately attempts queued writes when connectivity is available and also runs every 15 minutes.
- Foreground Firestore listeners pull role-scoped changes into Room.
- Request UUIDs make creates idempotent; status and balance updates use Firestore transactions.
- Rejected-request deletion uses a cloud tombstone so deletion reaches other devices.
- A competing manager update is resolved in favor of the already committed Firestore status.

Operational data such as the outbox, retry counters, local photo paths, and UI session cache remains device-local. All business records and attachments are persisted in Firebase.

## Firebase files

- `firestore.rules` – authenticated role and field-level database access
- `storage.rules` – owner/staff attachment access, image-only uploads, 10 MB limit
- `firestore.indexes.json` – Firestore index configuration
- `firebase.json` – deploy and emulator configuration
- `firebase/seed.js` – repeatable Auth/Firestore demo data provisioning

## Offline verification

1. Sign in once while online.
2. Disable connectivity and submit a leave request with a photo.
3. Confirm it appears immediately with `PENDING_SYNC`.
4. Restore connectivity.
5. Confirm the queue clears, the Firestore record appears, and the image exists in Storage.
6. Approve from a manager device and confirm the employee device receives the status and updated balance.
