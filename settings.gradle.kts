rootProject.name = "gradle-dependency-resolution-plugin"

pluginManagement {
    repositories {
        maven {
            val MAVEN_DOWNLOAD_URL = "MAVEN_DOWNLOAD_URL"
            val MAVEN_USER = "MAVEN_USER"
            val MAVEN_PASSWORD = "MAVEN_PASSWORD"

            val mavenDownloadURL = System.getenv(MAVEN_DOWNLOAD_URL)
            if (mavenDownloadURL == null) {
                throw GradleException("$MAVEN_DOWNLOAD_URL has to be set in environment")
            }
            var mavenUser = System.getenv(MAVEN_USER)
            if (mavenUser.isNullOrBlank()) {
                logger.info("$MAVEN_USER is not set in environment. Setting to \"x\"")
                mavenUser = "x"
            }
            var mavenPassword = System.getenv(MAVEN_PASSWORD)
            if (mavenPassword.isNullOrBlank()) {
                logger.info("$MAVEN_PASSWORD is not set in environment. Setting to \"x\"")
                mavenPassword = "x"
            }

            url = uri(mavenDownloadURL)
            credentials {
                username = mavenUser
                password = mavenPassword
            }
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            val MAVEN_DOWNLOAD_URL = "MAVEN_DOWNLOAD_URL"
            val MAVEN_USER = "MAVEN_USER"
            val MAVEN_PASSWORD = "MAVEN_PASSWORD"

            val mavenDownloadURL = System.getenv(MAVEN_DOWNLOAD_URL)
            if (mavenDownloadURL == null) {
                throw GradleException("$MAVEN_DOWNLOAD_URL has to be set in environment")
            }
            var mavenUser = System.getenv(MAVEN_USER)
            if (mavenUser.isNullOrBlank()) {
                logger.info("$MAVEN_USER is not set in environment. Setting to \"x\"")
                mavenUser = "x"
            }
            var mavenPassword = System.getenv(MAVEN_PASSWORD)
            if (mavenPassword.isNullOrBlank()) {
                logger.info("$MAVEN_PASSWORD is not set in environment. Setting to \"x\"")
                mavenPassword = "x"
            }

            url = uri(mavenDownloadURL)
            credentials {
                username = mavenUser
                password = mavenPassword
            }
        }
    }
}
