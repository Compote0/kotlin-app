## 📱 Kotlin Game Search App
A mobile application built with Kotlin, Jetpack Compose, and the RAWG API that allows users to search for games, explore categories, and see detailed game information.

## 🚀 Features
- 🔍 Search Games by name using the RAWG API.
- 🎮 Explore Categories: Best of the Year, Popular in 2023, All-Time Top 250.
- 📊 Sort Games: By relevance, popularity, rating, release date, and more.
- 📦 Pull-to-Refresh and Load More on Scroll.
- 🎨 Material 3 UI Design with Jetpack Compose.
- 🌙 Dark Mode Support.

## 📸 Screenshots


## 🛠️ Technologies Used
Kotlin - Main programming language
Jetpack Compose - For building modern UI
RAWG API - Game search and details provider
Android SDK - App development platform
Accompanist SwipeRefresh - For pull-to-refresh functionality

## 📦 Setup & Installation
Clone the repository: 

Install dependencies: Ensure you have the correct SDK version and libraries installed.

Run the application: Use an emulator or physical device.

## 🔧 Configuration
API Key Setup:

Got to https://rawg.io/apidocs and create your API key. Replace the API key in the configuration file Config.kt:

```
object Config {
    const val API_KEY = "YOUR_RAWG_API_KEY"
}
```
Build & Run:

Ensure you have an internet connection.

📂 Project Structure  
📂 courskotlin  
 ├── 📂 app  
 │    ├── 📂 src  
 │    │    ├── 📂 main  
 │    │    │    ├── 📂 java/com/example/courskotlin  
 │    │    │    │    ├── 📂 config         // Config File (API Key)  
 │    │    │    │    ├── 📂 http          // API Requests  
 │    │    │    │    ├── 📂 model         // Data Model  
 │    │    │    │    ├── 📂 ui            
 |    │    │    │    │    ├── 📂 screens          // Screens of the app  
 |    │    │    │    │    ├── 📂 theme            // Theme of the app  
 │    │    │    │    ├── 📂 ui            // API Requests  
 │    │    │    │    ├── 📂 utils         // Utils  
 │    │    │    │    ├── 📂 viewmodel     // ViewModel Logic  
 │    │    │    │    └── MainActivity.kt  // Main App Entry  
 │    │    │    └── 📂 res  
 │    │    │        └── layout, drawable, etc.  
 │    │    └── AndroidManifest.xml  
 └── build.gradle (Project)  
 └── settings.gradle  


If you like this project, give it a ⭐ on GitHub! 🚀🎮
