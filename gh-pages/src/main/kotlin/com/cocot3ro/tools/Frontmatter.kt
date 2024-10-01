package com.cocot3ro.tools

data class Frontmatter(
    //  All pages
    var externalLinkIcon: Boolean? = null,
    var navbar: Boolean? = null,
    var pageClass: String? = null,

    //  Home page
    var home: Boolean? = null,
    var heroImage: String? = null,
    var heroImageDark: String? = null,
    var heroAlt: String? = null,
    var heroHeight: Int? = null,
    var heroText: String? = null,
    var tagline: String? = null,
    var actions: List<Action>? = null,
    var features: List<Feature>? = null,
    var footer: String? = null,
    var footerHtml: Boolean? = null,

    //  Normal page
    var editLink: Boolean? = null,
    var editLinkPattern: String? = null,
    var lastUpdated: Boolean? = null,
    var contributors: Boolean? = null
) {

    override fun toString(): String {
        return StringBuilder().apply {
            append("---${System.lineSeparator()}")

            externalLinkIcon?.let { append("externalLinkIcon: $it${System.lineSeparator()}") }
            navbar?.let { append("navbar: $it${System.lineSeparator()}") }
            pageClass?.let { append("pageClass: $it${System.lineSeparator()}") }

            home?.let { append("home: $it${System.lineSeparator()}") }
            heroImage?.let { append("heroImage: $it${System.lineSeparator()}") }
            heroImageDark?.let { append("heroImageDark: $it${System.lineSeparator()}") }
            heroAlt?.let { append("heroAlt: $it${System.lineSeparator()}") }
            heroHeight?.let { append("heroHeight: $it${System.lineSeparator()}") }
            heroText?.let { append("heroText: $it${System.lineSeparator()}") }
            tagline?.let { append("tagline: $it${System.lineSeparator()}") }
            actions?.let {
                append("actions:${System.lineSeparator()}")
                append(it.joinToString(System.lineSeparator()) { action -> action.toString() })
                append(System.lineSeparator())
            }
            features?.let {
                append("features:${System.lineSeparator()}")
                append(it.joinToString(System.lineSeparator()) { feature -> feature.toString() })
                append(System.lineSeparator())
            }
            footer?.let { append("footer: $it${System.lineSeparator()}") }
            footerHtml?.let { append("footerHtml: $it${System.lineSeparator()}") }

            editLink?.let { append("editLink: $it${System.lineSeparator()}") }
            editLinkPattern?.let { append("editLinkPattern: $it${System.lineSeparator()}") }
            lastUpdated?.let { append("lastUpdated: $it${System.lineSeparator()}") }
            contributors?.let { append("contributors: $it${System.lineSeparator()}") }

            append("---")
        }.toString()
    }
}

data class Action(
    var text: String,
    var link: String,
    var type: ActionType? = null
) {

    enum class ActionType {
        PRIMARY,
        SECONDARY;

        override fun toString(): String {
            return name.lowercase()
        }
    }

    override fun toString(): String {
        return StringBuilder().apply {
            append("  - text: $text${System.lineSeparator()}")
            append("    link: $link${System.lineSeparator()}")
            type?.let {
                append("    type: $it")
            }
        }.toString()
    }

}

data class Feature(
    var title: String,
    var details: String
) {

    override fun toString(): String {
        return StringBuilder().apply {
            append("  - title: $title${System.lineSeparator()}")
            append("    details: $details")
        }.toString()
    }

}