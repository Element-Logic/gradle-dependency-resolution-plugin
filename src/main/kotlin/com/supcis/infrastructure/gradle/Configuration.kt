package com.supcis.infrastructure.gradle

import org.gradle.api.GradleException
import org.gradle.api.artifacts.repositories.AuthenticationSupported
import org.gradle.api.credentials.HttpHeaderCredentials
import org.gradle.authentication.http.HttpHeaderAuthentication
import org.gradle.internal.jvm.inspection.JvmVendor.KnownJvmVendor

object Configuration {
    private val MAVEN_AUTHENTICATION = "MAVEN_AUTHENTICATION"
    private val MAVEN_AUTHENTICATION_VIA_HEADER = "HttpHeader"
    private val MAVEN_DOWNLOAD_URL = "MAVEN_DOWNLOAD_URL"
    private val MAVEN_USER = "MAVEN_USER"
    private val MAVEN_PASSWORD = "MAVEN_PASSWORD"

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
}

fun AuthenticationSupported.setAuth() {
    if (Configuration.isAuthTypeHeader) {
        credentials(HttpHeaderCredentials::class.java) {
            it.name = "Authorization"
            it.value = "Bearer " + Configuration.password
        }
        authentication {
            it.create("header", HttpHeaderAuthentication::class.java)
        }
    } else {
        credentials {
            it.username = Configuration.username
            it.password = Configuration.password
        }
    }
}
