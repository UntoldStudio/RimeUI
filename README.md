# RimeUI 
[![](https://jitpack.io/v/UntoldStudio/RimeUI.svg)](https://jitpack.io/#UntoldStudio/RimeUI)
![LWJGL](https://img.shields.io/badge/LWJGL-3.3.3-red)
![Gson](https://img.shields.io/badge/Gson-2.10.1-blue)
![Log4j](https://img.shields.io/badge/Log4j-2.24.1-brown)
![SLF4J](https://img.shields.io/badge/SLF4J-2.0.9-orange)

RimeUI is an embedded UI library that only requires calling one rendering callback and one window handle per frame. The core module is the module you need to rely on, and the neoforge module is a Minecraft Mod bind, application Module is a Independent Editor

Note: This library requires the host to provide dependencies for LWJGL and JOML and GSON

To use this library, add the following to your build script:

```kotlin
repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.UntoldStudio:RimeUI:${version}")
}
```

Documentation is available [here](https://untoldstudio.github.io/RimeUI).

If you encounter any bugs, feel free to open an issue!

This library was originally named SimpleUI, and its final outcome is version v1.0.2, so the version number of this library starts from v1.1.0

This project is licensed under the Apache2.0