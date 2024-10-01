package com.cocot3ro.tools

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.math.pow

fun main() {
    val gson = GsonBuilder()
        .setPrettyPrinting()
        .create()

    val data = Site::class.java.getResource("/data.json")!!.readText()

    val site = gson.fromJson(data, Site::class.java)

    site.pages.let { pages ->

        pages["root"]!!.apply {
            val docs = Path.of("src/main/resources/docs")

            Files.newBufferedWriter(docs.resolve("index.md"))
                .use { it.write(frontmatter.toString()) }
        }

        generatePage(pages["core"]!!)
        generatePage(pages["moduloAlmacenAndroid"]!!)
        generatePage(pages["moduloAlmacenDesktop"]!!)
    }

    val target = Path.of("docs").toFile()
    target.resolve("core").deleteRecursively()
    target.resolve("modulo-almacen").deleteRecursively()
    target.resolve("index.md").delete()
    target.resolve("versions.json").delete()

    val generatedDocs = Path.of("src/main/resources/docs").toFile()
    generatedDocs.copyRecursively(Path.of("docs").toFile(), overwrite = true)

    val versionsMap: Map<String, Version> = mapOf(
        "core" to site.pages["core"]!!.version!!,
        "modulo-almacen-android" to site.pages["moduloAlmacenAndroid"]!!.version!!,
        "modulo-almacen-desktop" to site.pages["moduloAlmacenDesktop"]!!.version!!
    )
    File(target, "versions.json").bufferedWriter().use { it.write(gson.toJson(versionsMap)) }
}

fun generatePage(page: Page) {
    val gson = Gson()

    val client = OkHttpClient()

    page.run {

        val apiBaseUrl = "https://api.github.com/repos/cocot3ro"

        val latestReleaseRequest = Request.Builder()
            .url("$apiBaseUrl/$repo/releases/latest")
            .build()

        client.newCall(latestReleaseRequest).execute().use { response ->
            val latestRelease = gson.fromJson(response.body?.charStream(), Release::class.java)

            this.frontmatter.tagline = latestRelease.tagName
            this.frontmatter.actions!![0].link = latestRelease.assets[0].browserDownloadUrl
            this.version = Version(latestRelease.tagName.substring(1), latestRelease.assets[0].browserDownloadUrl)
        }
        val releasesRequest = Request.Builder()
            .url("$apiBaseUrl/$repo/releases")
            .build()

        client.newCall(releasesRequest).execute().use { response ->
            val releasesList = gson.fromJson(response.body?.charStream(), Array<Release>::class.java)
                .sortedByDescending { release ->
                    release.tagName
                        .substring(1)
                        .split('.')
                        .map { it.toInt() }
                        .mapIndexed { index: Int, i: Int -> (10.0.pow(-2 * index + 4)).toInt() * i }
                        .reduce { acc, i -> acc + i }
                }

            val separator = """
                    
                    ---
                    
                """.trimIndent()

            val release = """
                    ### VERSIÓN %s (%s)
                    
                    %s
                """.trimIndent()

            val releasesMdStrings = releasesList.map {
                release.format(
                    it.tagName.substring(1),
                    it.publishedAt.substring(0, 10).split('-').reversed().joinToString("-"),
                    it.body.takeUnless { body -> body.isBlank() } ?: "Sin descripción"
                )
            }.flatMap { listOf(it, separator) }
                .dropLast(1)

            val docs = Path.of("src/main/resources/docs${this.path}")

            Files.newBufferedWriter(docs.resolve("_releases.md"))
                .use { writer ->
                    writer.write(releasesMdStrings.joinToString(System.lineSeparator()))
                }

            Files.newBufferedWriter(docs.resolve("index.md"))
                .use { writer ->
                    writer.write(frontmatter.toString())
                    writer.write(System.lineSeparator())
                    writer.write(System.lineSeparator())
                    writer.write("<!-- @include: ./_content.md -->")
                }
        }
    }
}