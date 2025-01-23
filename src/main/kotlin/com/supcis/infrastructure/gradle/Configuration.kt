package com.supcis.infrastructure.gradle

import org.gradle.api.GradleException
import org.gradle.api.artifacts.repositories.AuthenticationSupported
import org.gradle.api.credentials.HttpHeaderCredentials
import org.gradle.api.credentials.PasswordCredentials
import org.gradle.authentication.http.HttpHeaderAuthentication
import org.gradle.internal.jvm.inspection.JvmVendor.KnownJvmVendor

object Configuration {
    private const val MAVEN_AUTHENTICATION = "MAVEN_AUTHENTICATION"
    private const val MAVEN_AUTHENTICATION_VIA_HEADER = "HttpHeader"
    private const val MAVEN_DOWNLOAD_URL = "MAVEN_DOWNLOAD_URL"
    private const val MAVEN_USER = "MAVEN_USER"
    private const val MAVEN_PASSWORD = "MAVEN_PASSWORD"
    private const val GRADLE_REMOTE_BUILD_CACHE_URL = "GRADLE_REMOTE_BUILD_CACHE_URL"
    private const val IS_PUSH_TO_REMOTE_BUILD_CACHE = "PUSH_TO_REMOTE_BUILD_CACHE"

    internal val DEFAULT_JDK_VENDOR = KnownJvmVendor.ADOPTIUM

    val isAuthTypeHeader: Boolean by lazy {
        MAVEN_AUTHENTICATION_VIA_HEADER == System.getenv(MAVEN_AUTHENTICATION)
    }

    val downloadUrl: String by lazy {
        System.getenv(MAVEN_DOWNLOAD_URL)
            ?: throw GradleException("[$MAVEN_DOWNLOAD_URL] has to be set in environment")
    }

    val username: String? by lazy {
        System.getenv(MAVEN_USER) ?: "x"
    }

    val password: String? by lazy {
        System.getenv(MAVEN_PASSWORD) ?: "x"
    }

    val remoteBuildCacheUrl: String? by lazy {
        System.getenv(GRADLE_REMOTE_BUILD_CACHE_URL)
    }

    val isPushToRemoteBuildCache: Boolean by lazy {
        System.getenv(IS_PUSH_TO_REMOTE_BUILD_CACHE).toBoolean()
    }
}

fun AuthenticationSupported.setAuth() {
    if (Configuration.isAuthTypeHeader) {
        credentials(HttpHeaderCredentials::class.java) {
            it.setAuth()
        }
        authentication {
            it.create("header", HttpHeaderAuthentication::class.java)
        }
    } else {
        credentials {
            it.setAuth()
        }
    }
}

fun HttpHeaderCredentials.setAuth() {
    name = "Authorization"
    value = "Bearer " + Configuration.password
}

fun PasswordCredentials.setAuth() {
    username = Configuration.username
    password = Configuration.password
}
