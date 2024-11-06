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

```settings.gralde.kts
plugins {
    id("com.supcis.java-toolchain-resolver") version "1.0.0"
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

```settings.gralde.kts
plugins {
    id("com.supcis.dependency-resolution") version "1.0.0"
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

```settings.gralde.kts
plugins {
    id("com.supcis.plugin-resolution") version "1.0.0"
}
```

### Combined Resolution Plugin

The Combined Resolution Plugin is a convenience plugin that applies all the three plugins above.

#### How To Use

Add the following lines to the `settings.gradle.kts`:

```settings.gralde.kts
plugins {
    id("com.supcis.resolution") version "1.0.0"
}
```

# Release History

## 1.0.0 initial release
