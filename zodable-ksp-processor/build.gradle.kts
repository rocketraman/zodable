plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.jetbrains.kotlinx.kover")
    id("com.google.devtools.ksp")
    id("com.vanniktech.maven.publish")
}

publishing {
    repositories {
        val devhausRepoUser: String by project
        val devhausRepoPassword: String by project

        maven {
            url = if (version.toString().endsWith("-SNAPSHOT")) {
                uri("https://repo.devhaus.com/repository/maven-snapshots/")
            } else {
                uri("https://repo.devhaus.com/repository/maven-releases/")
            }

            name = "devhaus"
            credentials {
                username = devhausRepoUser
                password = devhausRepoPassword
            }
        }
    }
}

mavenPublishing {
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.CENTRAL_PORTAL)
    //signAllPublications()
    pom {
        name.set("zodable-ksp-processor")
        description.set("KSP processor for Zodable Gradle plugin.")
        url.set(project.ext.get("url")?.toString())
        licenses {
            license {
                name.set(project.ext.get("license.name")?.toString())
                url.set(project.ext.get("license.url")?.toString())
            }
        }
        developers {
            developer {
                id.set(project.ext.get("developer.id")?.toString())
                name.set(project.ext.get("developer.name")?.toString())
                email.set(project.ext.get("developer.email")?.toString())
                url.set(project.ext.get("developer.url")?.toString())
            }
        }
        scm {
            url.set(project.ext.get("scm.url")?.toString())
        }
    }
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    api("com.google.devtools.ksp:symbol-processing-api:2.2.20-2.0.4")
    api(project(":zodable-annotations"))
}
