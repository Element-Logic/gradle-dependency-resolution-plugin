group = "com.supcis.plugins.gradle"

version = "1.0.0"

plugins {
    `java-gradle-plugin`

    id("org.jetbrains.kotlin.jvm") version "2.0.21"

    id("com.gradle.plugin-publish") version "1.3.0"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
    withSourcesJar()
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

gradlePlugin {
    website = "https://github.com/Element-Logic/gradle-dependency-resolution-plugin"
    vcsUrl = "https://github.com/Element-Logic/gradle-dependency-resolution-plugin.git"

    plugins.create("JavaToolchainResolver") {
        displayName = "Java Toolchain Resolver Plugin"
        id = "com.supcis.java-toolchain-resolver"
        implementationClass = "com.supcis.infrastructure.gradle.JavaToolchainResolverPlugin"
        description = """
            Configures a JavaToolchainResolver that is configured via environment variables:
            - MAVEN_DOWNLOAD_URL: the base URL for the maven repository
            - MAVEN_USER: the username for the maven repository (optional)
            - MAVEN_PASSWORD: the password for the maven repository (optional)

            The JDK is expected to be in the form of a maven coordinate:
            [MAVEN_DOWNLOAD_URL]/jdk/vendor/version/os/architecture/vendor-jdk-version-os-architecture.zip
        """.trimIndent()
        tags.add("toolchain resolver")
    }
    plugins.create("DependencyResolutionPlugin") {
        displayName = "Dependency Resolution Plugin"
        id = "com.supcis.dependency-resolution"
        implementationClass = "com.supcis.infrastructure.gradle.DependencyResolutionPlugin"
        description = """
            Adds a maven repository to the dependency resolution configuration that is configured
            via environment variables:
            - MAVEN_DOWNLOAD_URL: the base URL for the maven repository
            - MAVEN_USER: the username for the maven repository (optional)
            - MAVEN_PASSWORD: the password for the maven repository (optional)
        """.trimIndent()
        tags.add("dependency resolution")
    }
    plugins.create("PluginResolutionPlugin") {
        displayName = "Plugin Resolution Plugin"
        id = "com.supcis.plugin-resolution"
        implementationClass = "com.supcis.infrastructure.gradle.PluginResolutionPlugin"
        description = """
            Adds a maven repository to the plugin resolution configuration that is configured
            via environment variables:
            - MAVEN_DOWNLOAD_URL: the base URL for the maven repository
            - MAVEN_USER: the username for the maven repository (optional)
            - MAVEN_PASSWORD: the password for the maven repository (optional)
        """.trimIndent()
        tags.add("plugin resolution")
    }
    plugins.create("CombinedResolutionPlugin") {
        displayName = "Combined Resolution Plugin"
        id = "com.supcis.resolution"
        implementationClass = "com.supcis.infrastructure.gradle.CombinedResolutionPlugin"
        description = """
            Applies all three resolution plugins: JavaToolchainResolver, DependencyResolutionPlugin
            and PluginResolutionPlugin. This is just for convenience.
        """.trimIndent()
        tags.addAll("toolchain resolver", "dependency resolution", "plugin resolution")
    }
}
