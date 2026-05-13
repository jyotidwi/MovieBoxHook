<div align="center">

# 🎬 MovieBox Hook 

**Unlock the full potential of MovieBox with this advanced Xposed module.**

[![Version](https://img.shields.io/badge/Version-0.0.1-blue.svg?style=for-the-badge)](https://github.com/jyotidwi/MovieBoxHook/releases)
[![Android](https://img.shields.io/badge/Android-7.0%2B-green.svg?style=for-the-badge)](https://www.android.com/)
[![Xposed](https://img.shields.io/badge/Xposed-Supported-orange.svg?style=for-the-badge)](https://github.com/LSPosed/LSPosed)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)](https://opensource.org/licenses/MIT)

*Compatible with **Xposed**, **LSPosed**, and **LSPatch** (Non-Root).*

</div>

---

## 🔥 Overview

**MovieBox Hook** is an advanced Xposed module designed to unlock all **VIP & Premium** features within the MovieBox application. By utilizing intelligent hooking mechanisms, dynamic fallback logic, and obfuscation detection, the module ensures broad compatibility across multiple app versions while maintaining complete stability.

---

## ✨ Key Features

| Feature | Description |
| :--- | :--- |
| 💎 **VIP & Premium Unlock** | Grants full access to all exclusive premium content. |
| 🎥 **High Definition Output** | Enables streaming in HD, Full HD, and true 4K resolutions. |
| 🚫 **Ad-Free Experience** | Completely blocks and removes all annoying advertisements. |
| 📥 **Unlimited Downloads** | Unlocks the ability to download content without restrictions. |
| 🧠 **Smart Hook Detection** | Automatically bypasses modern obfuscation techniques. |
| 🧩 **Dynamic Fallback** | Uses constructor-fallback for maximum compatibility. |
| ⚡ **Lightweight & Stable** | Minimal footprint with zero impact on system performance. |
| 🔐 **No Root Required** | Fully supports **LSPatch** for non-rooted environments. |

---

## 📦 Installation Guide

Choose the method that best suits your device configuration:

### 🟢 Root Method (LSPosed)

> **Requirements:** Rooted Android device, LSPosed installed, MovieBox installed.

1. Install the **MovieBox Hook** module APK.
2. Open the **LSPosed Manager**.
3. Enable the module.
4. Scope the module to the target app: `com.moviebox.app`.
5. Reboot your device.
6. Open MovieBox and enjoy your VIP status! 🎉

### 🟣 Non-Root Method (LSPatch)

#### Option A: Patch Installed App
1. Open **LSPatch**.
2. Navigate to **Manage Apps** and select **MovieBox**.
3. Tap on **Patch App**.
4. Add the **MovieBox Hook** module.
5. Tap **Patch** and install the resulting app.
6. Open MovieBox and verify your VIP unlock.

#### Option B: Patch External APK (🌟 Recommended)
1. Download the original MovieBox APK file.
2. Open **LSPatch** and select **Patch APK**.
3. Choose the downloaded MovieBox APK.
4. Add the **MovieBox Hook** module.
5. Select **Portable Mode** *(Highly Recommended)*.
6. Tap **Patch** and install the generated output APK.
7. Open MovieBox and enjoy all premium features!

> [!WARNING]  
> **Notes on LSPatch:**
> - You will need to re-patch the app every time MovieBox releases an update.
> - Some system security apps may flag the modified APK as a risk (this is a false positive).
> - This method works 100% without requiring root access.

---

## 🛠️ Build From Source

Want to compile the module yourself? It's simple:

```bash
# Clone the repository
git clone https://github.com/jyotidwi/MovieBoxHook.git

# Navigate into the project directory
cd MovieBoxHook

# Build the debug APK
./gradlew assembleDebug
```

> **APK Output Location:** `app/build/outputs/apk/debug/app-debug.apk`

---

## 👨‍💻 Author

Developed with ❤️ by **JyotiDwi**

[![Telegram](https://img.shields.io/badge/Telegram-Join_Chat-blue.svg?style=for-the-badge&logo=telegram)](https://t.me/jyotidwi)
[![GitHub](https://img.shields.io/badge/GitHub-Profile-black.svg?style=for-the-badge&logo=github)](https://github.com/jyotidwi)

---

## 📜 License

This project is licensed under the **MIT License**. See the `LICENSE` file for more details.

---

## ⭐ Support the Project

If you find this module useful, please consider supporting the project:

- 🌟 **Star** the repository to show your appreciation!
- 🐞 **Report** any issues you encounter to help improve the module.
- 🔧 **Submit** pull requests with bug fixes or new features.
- 📣 **Share** the project with the community.
