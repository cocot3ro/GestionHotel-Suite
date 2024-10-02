package com.cocot3ro.tools

data class Site(
    var pages: MutableMap<String, Page>
)

data class Page(
    var path: String,
    var repo: String,
    var frontmatter: Frontmatter
)