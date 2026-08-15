# RimeUI

RimeUI is an embedded UI library that only requires a rendering callback called per frame and a window handle. It also has other modes, such as creating windows on its own

Note: This library requires the host to provide dependencies for LWJGL and JOML

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

This library was originally named SimpleUI, and its final outcome is version v1.0.2, so the version number of this library starts from v1.1.0

This project is licensed under the GNU Lesser General Public License v3.0 (LGPLv3). Since LGPLv3 is based on GPLv3, the full text of GPLv3 can be found at [GNU GPLv3 License](https://www.gnu.org/licenses/gpl-3.0.txt).