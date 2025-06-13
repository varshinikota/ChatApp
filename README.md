# 📱 ChatApp

**ChatApp** is an innovative **Android-based messaging application** crafted for **offline communication**, empowering users to stay connected even in areas with limited or no internet access.

This project demonstrates key concepts in Android development using **Kotlin**, the **Android SDK**, and **SQLite** for local data storage.

---

## 🚀 Key Features

✅ **Offline Messaging** — Communicate without an active internet connection.  
✅ **User Authentication** — Register and log in securely.  
✅ **Friend Management** — Add, remove, and manage friends locally.  
✅ **Chat Functionality** — One-to-one chat with emoji support.  
✅ **Profile Customization** — Personalize your user profile.  
✅ **Local Storage** — SQLite database handles credentials and messages.  
✅ **Dynamic UI** — Built using RecyclerView and ListView for smooth chat displays.

---

## ⚙️ Tech Stack

- **Language:** Kotlin  
- **Framework:** Android SDK  
- **Database:** SQLite (for local persistence)  
- **UI:** Android XML layouts, custom drawables, emoji support  
- **Components:** RecyclerView, ListView

---

## 📊 Performance & Limitations

- Efficient local data handling for user and message management.
- Robust offline performance for individual devices.
- Limited scalability due to offline-first architecture.
- Plain-text password storage **must be secured** in future updates.

---

## 🔒 Security Considerations

- Current version stores passwords in plain text. **Implement secure hashing and salting ASAP.**
- No network-level encryption (offline-only design) — plan to add secure syncing when integrating online features.

---

## ✨ Future Enhancements

- Backend server for real-time messaging and syncing.
- Multimedia sharing (images, videos).
- Improved encryption for stored and transmitted data.
- Modernize UI with advanced material design components.

---

## 📚 Key Learnings

This project demonstrates:
- Kotlin’s concise syntax and object-oriented features.
- Android SDK’s powerful tools for building offline apps.
- Real-world considerations for secure offline storage and smooth UI design.

---

## 📂 How to Run

1. **Clone this repository:**
   ```bash
   git clone https://github.com/varshinikota/ChatApp.git

2. Open in Android Studio.

3. Build & run on an emulator or physical Android device.
