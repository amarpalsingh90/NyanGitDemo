package com.nayangitdemo.model

data class PopularGitRepo(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)