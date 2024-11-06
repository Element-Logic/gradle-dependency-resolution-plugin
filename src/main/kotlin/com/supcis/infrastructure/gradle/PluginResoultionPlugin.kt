package com.supcis.infrastructure.gradle

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import java.net.URI

/**
 * This Plugin adds a maven repo to the PluginManagement that is configured via
 * environment variables, see [Configuration].
 */
abstract class PluginResolutionPlugin : Plugin<Settings> {

    override fun apply(settings: Settings) {
        settings.pluginManagement {
            it.repositories {
                it.maven {
                    it.url = URI(Configuration.downloadUrl)
                    it.setAuth()
                }
            }
        }
    }
}
