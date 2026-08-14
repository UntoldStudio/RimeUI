# SimpleUI

SimpleUI is a UI library for NeoForge. We want to make Minecraft UI creation easier.

> **Warning:** Currently, none of the library's GUI nodes can modify vanilla GUIs, and all GUI nodes will render on top of vanilla GUIs.

If you want to use this lib, you should:

```kotlin
repositories {
    maven("https://jitpack.io")
}
dependencies {
    implementation("com.github.UntoldStudio:SimpleUI:${version}")
}
```

If you encounter bugs, feel free to open an issue!

This project is licensed under the GNU Lesser General Public License v3.0.

Since LGPLv3 is based on GPLv3, the full text of GPLv3 can be found at https://www.gnu.org/licenses/gpl-3.0.txt