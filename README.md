# LeaveFlow – Android Leave Management System

## Getting Started

### Android App (Kotlin + Jetpack Compose)
1. Open `d:\HND SE\MAD\Leave Management System\` in **Android Studio Hedgehog** (or newer)
2. Wait for Gradle sync to complete (first sync downloads ~200MB of dependencies)
3. Connect an emulator or physical device (API 24+)
4. Run the app → `▶ Run 'app'`

### Node.js Backend (Optional for network sync)
```bash
cd backend
npm install
node server.js
# Server starts at http://localhost:3000
# Android emulator accesses it via http://10.0.2.2:3000
```

---

## Demo Login Credentials

| Role         | Email                          | Password    |
|--------------|--------------------------------|-------------|
| **Employee** | john.doe@leaveflow.com         | Pass@1234   |
| **Employee** | alice.perera@leaveflow.com     | Pass@1234   |
| **Manager**  |  sarah.smith@leaveflow.com     | Pass@1234   |
| **HR Admin** | admin.hr@leaveflow.com         | Pass@1234   |

---

## Architecture

```
Clean Architecture + MVVM
├── UI Layer         → Jetpack Compose Screens + ViewModels
├── Domain Layer     → Business models + Result sealed class
├── Data Layer       → Room (local) + Retrofit (remote)
└── Worker Layer     → WorkManager (background sync)
```

### Key Technical Features
- **Room Database** – 4 entities, 4 DAOs, full CRUD
- **BCrypt** – Password hashing (cost factor 12)
- **Hilt** – Dependency injection throughout
- **CameraX** – Document capture with preview/retake
- **FusedLocationProvider** – GPS coordinate capture
- **WorkManager** – Periodic + manual background sync
- **Kotlin Coroutines + Flow** – All async operations
- **DataStore** – Secure session persistence
- **Retrofit + OkHttp** – REST API with Gson parsing

---

## Permissions Required
- `CAMERA` – Document/certificate capture
- `ACCESS_FINE_LOCATION` – GPS attachment
- `INTERNET` – Remote API sync
- `ACCESS_NETWORK_STATE` – Connectivity checks

---

## Test Scenarios

### Employee Flow
1. Login → Employee Dashboard
2. View leave balances (Annual 15, Casual 8, Medical 14)
3. Submit leave → fills form → attach GPS + photo → submit
4. View history → filter by status → expand details
5. Delete rejected request

### Manager Flow
1. Login → Manager Dashboard (pending count shown)
2. Tap a request → review all details (GPS, photo indicators)
3. Add comment → Approve or Reject
4. Confirmation dialog before action

### HR Flow
1. Login → HR Dashboard
2. View summary stats (Total / Pending / Approved / Rejected)
3. Filter all requests by status

### Offline Flow
1. Turn off WiFi on emulator
2. Submit new leave request → saved to Room with PENDING_SYNC status
3. Turn WiFi back on → WorkManager automatically syncs → status → SYNCED
