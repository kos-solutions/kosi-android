package com.kosi.app.api

import kotlinx.serialization.Serializable

@Serializable
data class StoryResponse(
    val story: String
)
