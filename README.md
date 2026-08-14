# SimpleUI

SimpleUI is a UI library for NeoForge that aims to make Minecraft UI creation easier.

> **Warning:** Currently, none of the library's GUI nodes can modify vanilla GUIs. All GUI nodes will render on top of vanilla GUIs.

To use this library, add the following to your build script:

```kotlin
repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.UntoldStudio:SimpleUI:${version}")
}
```

Documentation is available [here](https://untoldstudio.github.io/SimpleUI).

If you encounter any bugs, feel free to open an issue!

This project is licensed under the GNU Lesser General Public License v3.0 (LGPLv3). Since LGPLv3 is based on GPLv3, the full text of GPLv3 can be found at [GNU GPLv3 License](https://www.gnu.org/licenses/gpl-3.0.txt).