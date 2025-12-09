package com.kosi.app.api

import kotlinx.serialization.Serializable

@Serializable
data class StoryRequest(
    val child_name: String,
    val age: Int,
    val prompt: String
)
