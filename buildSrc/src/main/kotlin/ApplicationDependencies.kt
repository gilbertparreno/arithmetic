import ApplicationDependencyVersions.kotlin

object ApplicationDependencies {

    object GradlePlugins {
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin"
    }

    private const val kotlinStandardLib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlin"

    val dependencies = arrayListOf<String>().apply {
        add(kotlinStandardLib)
    }
}