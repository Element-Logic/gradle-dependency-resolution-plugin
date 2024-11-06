@file:Suppress("UnstableApiUsage")

package com.supcis.infrastructure.gradle

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.internal.jvm.inspection.JvmVendor.KnownJvmVendor
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.jvm.toolchain.JavaToolchainDownload
import org.gradle.jvm.toolchain.JavaToolchainRequest
import org.gradle.jvm.toolchain.JavaToolchainResolver
import org.gradle.jvm.toolchain.JavaToolchainResolverRegistry
import org.gradle.kotlin.dsl.jvm
import org.gradle.platform.OperatingSystem
import java.net.URI
import java.util.Optional
import javax.inject.Inject

/**
 * This Plugin adds a [JavaToolchainResolver] that is configured via environment variables,
 * see [MavenDownloadUrlJavaToolchainResolver].
 */
abstract class JavaToolchainResolverPlugin : Plugin<Settings> {

    @Inject
    protected abstract fun getToolchainResolverRegistry(): JavaToolchainResolverRegistry

    override fun apply(settings: Settings) {
        settings.plugins.apply("jvm-toolchain-management")

        val registry = getToolchainResolverRegistry()
        registry.register(MavenDownloadUrlJavaToolchainResolver::class.java)

        settings.toolchainManagement {
            it.jvm {
                javaRepositories {
                    it.repository("JavaToolchainResolverPlugin") {
                        it.resolverClass.set(MavenDownloadUrlJavaToolchainResolver::class.java)
                        it.setAuth()

                    }
                }
            }
        }
    }
}

/**
 * Provides a JDK-Download-URL based on
 *
 * - Env Configuration
 * - OS Requirements
 * - JVM Requirements
 *
 * The Download-URL is in form of a maven coordinate:
 *
 * --> [MAVEN_DOWNLOAD_URL]/jdk/vendor/version/os/architecture/vendor-jdk-version-os-architecture.zip
 * --> https://example.com/jdk/adoptium/17/windows/x86_64/adoptium-jdk-17-windows-x86_64.zip
 */
abstract class MavenDownloadUrlJavaToolchainResolver : JavaToolchainResolver {

    companion object {
        private const val MAVEN_COORDINATE_JAVA_PART = "jdk"
        private const val FILE_NAME = "jdk"
        private const val FILE_EXTENSION_ZIP = "zip"
        private const val FILE_EXTENSION_TAR = "tar.gz"
    }

    override fun resolve(request: JavaToolchainRequest): Optional<JavaToolchainDownload> {
        val baseUrl = Configuration.downloadUrl
        val vendor = determineVendor(request)
        val version = request.javaToolchainSpec.languageVersion.get()
        val os = request.buildPlatform.operatingSystem.name.lowercase()
        val architecture = request.buildPlatform.architecture.name.lowercase()
        val fileExtension = getFileExtension(request.buildPlatform.operatingSystem)

        val downloadUrl = createUrl(baseUrl, vendor, version, os, architecture, fileExtension)
        return Optional.of(JavaToolchainDownload { URI.create(downloadUrl) })
    }

    private fun determineVendor(request: JavaToolchainRequest): String {
        val requestVendor = request.javaToolchainSpec.vendor.get()

        val knownVendor = KnownJvmVendor.entries.find {
            requestVendor.matches(it.name)
        } ?: Configuration.DEFAULT_JDK_VENDOR

        return knownVendor.asJvmVendor().rawVendor.lowercase()
    }

    private fun getFileExtension(operatingSystem: OperatingSystem) = when (operatingSystem) {
        OperatingSystem.WINDOWS -> FILE_EXTENSION_ZIP
        else -> FILE_EXTENSION_TAR
    }

    private fun createUrl(
        baseUrl: String,
        vendor: String,
        version: JavaLanguageVersion?,
        os: String,
        architecture: String,
        fileExtension: String
    ) = "$baseUrl/" +
            "$MAVEN_COORDINATE_JAVA_PART/" +
            "$vendor/" +
            "$version/" +
            "$os/" +
            "$architecture/" +
            "$vendor-$FILE_NAME-$version-$os-$architecture.$fileExtension"
}
