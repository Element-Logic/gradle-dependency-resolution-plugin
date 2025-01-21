package com.supcis.infrastructure.gradle

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import java.net.URI
import org.gradle.caching.http.HttpBuildCache

/**
 * This Plugin adds a remote build cache that is configured via
 * environment variables, see [Configuration].
 */
abstract class RemoteBuildCachePlugin : Plugin<Settings> {

    override fun apply(settings: Settings) {
        val url = Configuration.remoteBuildCacheUrl ?: return

        settings.buildCache {
            it.remote(HttpBuildCache::class.java) {
                it.url = URI(url)
                it.credentials.setAuth()
                it.isPush = Configuration.isPushToRemoteBuildCache
            }
        }
    }
}
