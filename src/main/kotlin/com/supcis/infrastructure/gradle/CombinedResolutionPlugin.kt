package com.supcis.infrastructure.gradle

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings

/**
 * This Plugin is a convenience plugin to apply all three plugins [PluginResolutionPlugin],
 * [DependencyResolutionPlugin] and [JavaToolchainResolverPlugin].
 */
abstract class CombinedResolutionPlugin : Plugin<Settings> {

    override fun apply(settings: Settings) {
        settings.plugins.apply("com.supcis.plugin-resolution")
        settings.plugins.apply("com.supcis.dependency-resolution")
        settings.plugins.apply("com.supcis.java-toolchain-resolver")
        settings.plugins.apply("com.supcis.remote-build-cache")
    }
}
