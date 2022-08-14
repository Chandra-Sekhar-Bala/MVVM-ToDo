package com.sekhar.todo.app.repo.network

import com.squareup.moshi.Json

data class Motivation(
    @Json(name = "text") val title : String?,
    val author : String?
)
