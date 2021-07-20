plugins {
    kotlin("jvm")
    id("jps-compatible")
}

val compilerModules: Array<String> by rootProject.extra

dependencies {
    compile(project(":kotlin-build-common"))
    compile(project(":core:descriptors"))
    compile(project(":core:descriptors.jvm"))
    compile(project(":kotlin-compiler-runner"))
    compile(project(":daemon-common"))
    compile(project(":daemon-common-new"))
    compile(project(":kotlin-daemon-client"))
    compile(project(":kotlin-daemon"))
    compile(project(":compiler:frontend.java"))
    compile(project(":js:js.frontend"))
    compile(project(":kotlin-preloader"))
    compile(project(":idea:idea-jps-common"))
    compileOnly(intellijDep()) {
        includeJars("jdom", "trove4j", "jps-model", "platform-api", "util", "asm-all", rootProject = rootProject)
    }
    compileOnly(jpsStandalone()) { includeJars("jps-builders", "jps-builders-6") }
    testCompileOnly(project(":kotlin-reflect-api"))
    testApi(project(":compiler:incremental-compilation-impl"))
    testApi(projectTests(":compiler:tests-common"))
    testApi(projectTests(":compiler:incremental-compilation-impl"))
    testApi(commonDep("junit:junit"))
    testApi(project(":kotlin-test:kotlin-test-jvm"))
    testApi(projectTests(":kotlin-build-common"))
    testCompileOnly(jpsStandalone()) { includeJars("jps-builders", "jps-builders-6") }
    Ide.IJ {
        testApi(intellijDep("devkit"))
    }

    testApi(intellijDep())

    testApi(jpsBuildTest())
    compilerModules.forEach {
        testRuntime(project(it))
    }

    testRuntimeOnly(intellijPluginDep("java"))

    testRuntimeOnly(toolsJar())
    testRuntime(project(":kotlin-reflect"))
    testRuntime(project(":kotlin-script-runtime"))
}

sourceSets {
    "main" { projectDefault() }
    "test" {
        Ide.IJ {
            java.srcDirs("jps-tests/test")
        }
    }
}

projectTest(parallel = true) {
    // do not replace with compile/runtime dependency,
    // because it forces Intellij reindexing after each compiler change
    dependsOn(":kotlin-compiler:dist")
    dependsOn(":kotlin-stdlib-js-ir:packFullRuntimeKLib")
    workingDir = rootDir
}

testsJar {}
