# 🌍 Planet Watch Face - A Celestial Timepiece for Wear OS

### 🪐 Explore the Cosmos on Your Wrist!
Experience the wonder of space every time you check your watch! Planet Watch Face is an interactive, solar-system-inspired watch face built with **Jetpack Compose** for **Wear OS**. The planets orbit dynamically around the sun, and their positions are updated based on real-time astronomical data. Rotate your smartwatch's crown to interact with the cosmos in an immersive way!

---

## 🚀 Features

✨ **Live Solar System Simulation** – Planets orbiting the sun in real time.  
🔭 **Astronomically Accurate Data** – Fetches planetary positions & velocities from an API.  
🎨 **Customizable** – Adjust planet sizes and colors for a personalized experience.  
⌚ **Wear OS Optimized** – Smooth interactions using rotary input for seamless navigation.  
⚡ **Efficient & Lightweight** – Built with Jetpack Compose, Coroutines, and StateFlow for optimal performance.

---

## 🛠️ Technologies Used

- **Kotlin** – Modern, expressive language for Android development.
- **Jetpack Compose** – Declarative UI toolkit for building beautiful UIs.
- **Wear OS** – Optimized for smartwatch displays and interactions.
- **Retrofit** – For fetching planetary data from the NASA JPL Horizons API.
- **Coroutines** – For efficient background data fetching.
- **ViewModel & StateFlow** – For reactive UI updates and state management.

---

## 🏁 Getting Started

### 📌 Prerequisites
- **Android Studio (Latest Version)**
- **Wear OS Emulator or Physical Wear OS Device**

### 📥 Installation
1️⃣ Clone the repository:
   ```sh
   git clone https://github.com/jenxwa/Planet-Watch-Face.git
   ```
2️⃣ Open the project in **Android Studio**.
3️⃣ Select a **Wear OS Emulator** or **Wear OS Device** as the target.
4️⃣ Click **Run** to install and launch the watch face.

---

## 🗂 Project Structure

📂 **`MainActivity.kt`** – The main entry point handling UI rendering and interactions.  
📂 **`PlanetViewModel.kt`** – Manages planetary data and updates their positions.  
📂 **`data/`** – Contains data classes and API calls (Retrofit client).  
📂 **`presentation/theme/`** – Theming files for UI customization.  

---

## 🌌 Usage
- The watch face displays a **live solar system simulation**, with planetary positions based on **[NASA JPL Horizons API](https://ssd.jpl.nasa.gov/horizons/)**

---

## 🤝 Contributing
Got ideas to improve the watch face? Contributions are always welcome! Feel free to **open an issue** or **submit a pull request**. 🚀

---

## 📜 License
This project is licensed under the **MIT License**. See the `LICENSE` file for details.

---

## ⭐ Acknowledgements
- **[NASA JPL Horizons API](https://ssd.jpl.nasa.gov/horizons/)** – Provides real-time planetary data.
- **[Jetpack Compose](https://developer.android.com/jetpack/compose)** – Declarative UI toolkit for Android.
- **[Wear OS](https://developer.android.com/wear)** – Google’s smartwatch platform.

---

## 🎉 Elevate Your Watch Experience!
Turn your smartwatch into a **window to the universe**. Download, install, and embark on a celestial journey right from your wrist! 🪐✨
