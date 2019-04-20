package com.github.bassaer.simplebase.github

import java.io.Serializable

data class RepoResponse (
    var name: String,
    var language: String,
    var star: Int
): Serializable
