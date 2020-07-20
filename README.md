# Ockero
 
![logo](https://github.com/KogeLabs/Koge/blob/master/ockero_logo.png?raw=true)

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/773e224e434d411cb632f0829516cec4)](https://www.codacy.com/gh/KogeLabs/Ockero?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=KogeLabs/Ockero&amp;utm_campaign=Badge_Grade)
![Java CI with Gradle](https://github.com/KogeLabs/Koge/workflows/Java%20CI%20with%20Gradle/badge.svg)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.kogelabs/koge-jvm.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.kogelabs%22%20AND%20a:%22koge-jvm%22)


Ockero is a 2D OpenGL game framework developed in Kotlin that works in Windows, Linux and Mac OS X.

## Pre-Requirements
*   Java JDK
*   IntelliJ IDEA (community or Ultimate edition)
*   Gradle
 
## Getting Started 

![Mario](https://github.com/KogeLabs/Koge/blob/master/Mario-Ockero.png?raw=true)

In the ```build.gradle``` of your Idea project you want to add the following script to download Koge and their dependencies from the maven central.
```Groovy
repositories {
    mavenCentral()
}

dependencies {

    implementation 'com.github.kogelabs:ockero-jvm:<version>'
}
```
For more information on how to set up Ockero, please visit the [Getting Started](https://github.com/MoncefYabi/Koge/wiki/Getting-Started) page.

## Documentation
The [Wiki](https://github.com/MoncefYabi/Koge/wiki) contains all the information you'll need to write a Ockero game. You can contribute to the Wiki directly here on GitHub!
