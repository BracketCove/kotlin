description = "Kotlin Compiler Infrastructure for Scripting for embeddable compiler"

plugins {
    java
}

dependencies {
    embedded(project(":kotlin-scripting-compiler-impl")) { isTransitive = false }
    runtimeOnly(project(":kotlin-scripting-common"))
    runtimeOnly(project(":kotlin-scripting-jvm"))
    runtimeOnly(kotlinStdlib())
    runtimeOnly(commonDep("org.jetbrains.kotlinx", "kotlinx-coroutines-core"))
}

publish()

runtimeJar(rewriteDefaultJarDepsToShadedCompiler())
sourcesJar()
javadocJar()
