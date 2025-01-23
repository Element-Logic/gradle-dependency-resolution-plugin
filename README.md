# Gradle Dependency Resolution Plugin

This project contains three plugins that allow to configure a Java Toolchain Resolver, a Maven repo
for Dependency Resolution and a Maven repo for Plugin Resolution via environment variables. And
there is a fourth plugin that applies all three plugins.

## Plugins

### Java Toolchain Resolver Plugin

This Toolchain Resolver Plugin adds support for the
[Gradle Toolchain Provisioning](https://docs.gradle.org/current/userguide/toolchains.html) starting
from Gradle 8.

It adds a JavaToolchainResolver that is configured via environment variables:
- MAVEN_DOWNLOAD_URL: the base URL for the maven repository
- MAVEN_USER: the username for the maven repository (optional)
- MAVEN_PASSWORD: the password for the maven repository (optional)

The JDK is expected to be in the form of a maven coordinate:
[MAVEN_DOWNLOAD_URL]/jdk/vendor/version/os/architecture/vendor-jdk-version-os-architecture.zip

#### How To Use

Add the following lines to the `settings.gradle.kts`:

```settings.gradle.kts
plugins {
    id("com.supcis.java-toolchain-resolver") version "1.1.0"
}
```

### Dependency Resolution Plugin

The Dependency Resolution Plugin adds a maven repository to the dependency resolution configuration
that is configured via environment variables:
- MAVEN_DOWNLOAD_URL: the base URL for the maven repository
- MAVEN_USER: the username for the maven repository (optional)
- MAVEN_PASSWORD: the password for the maven repository (optional)

#### How To Use

Add the following lines to the `settings.gradle.kts`:

```settings.gradle.kts
plugins {
    id("com.supcis.dependency-resolution") version "1.1.0"
}
```

### Plugin Resolution Plugin

The Plugin Resolution Plugin adds a maven repository to the dependency resolution configuration
that is configured via environment variables:
- MAVEN_DOWNLOAD_URL: the base URL for the maven repository
- MAVEN_USER: the username for the maven repository (optional)
- MAVEN_PASSWORD: the password for the maven repository (optional)

#### How To Use

Add the following lines to the `settings.gradle.kts`:

```settings.gradle.kts
plugins {
    id("com.supcis.plugin-resolution") version "1.1.0"
}
```

### Remote Build Cache Plugin

The Remote Build Cache Plugin adds a remote build cache to the gradle configuration.
The cache is configured via environment variables:
- GRADLE_REMOTE_BUILD_CACHE_URL: the base URL for the remote build cache (optional)
- PUSH_TO_REMOTE_BUILD_CACHE: boolean value whether to push to the remote build cache
  or to use it only for pulling. (optional, default: false)
- MAVEN_USER: the username for the maven repository (optional)
- MAVEN_PASSWORD: the password for the maven repository (optional)

No remote cache is added if no `GRADLE_REMOTE_BUILD_CACHE_URL` is set.

You still need to activate the build cache in your project, see
[gradle user guide](https://docs.gradle.org/current/userguide/build_cache.html).


#### How To Use

Add the following lines to the `settings.gradle.kts`:

```settings.gradle.kts
plugins {
    id("com.supcis.remote-build-cache") version "1.1.0"
}
```

### Combined Resolution Plugin

The Combined Resolution Plugin is a convenience plugin that applies all the four plugins above.

#### How To Use

Add the following lines to the `settings.gradle.kts`:

```settings.gradle.kts
plugins {
    id("com.supcis.resolution") version "1.1.0"
}
```

# Release History

## 1.1.0 add remote build cache plugin

See description above

## 1.0.1 fix auth via header

Fixes the authentication via header to solve the error:

>Cannot create a Authentication named 'header' because this container does not support creating
>elements by name alone. Please specify which subtype of Authentication to create. Known subtypes
>are: AwsImAuthentication, BasicAuthentication, DigestAuthentication, HttpHeaderAuthentication

## 1.0.0 initial release
