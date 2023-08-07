package com.prmto.inviostaj.data.remote.dto

import com.squareup.moshi.Json

data class ListResponse<T>(
    val page: Int,
    val results: List<T>,
    @Json(name = "total_results") val totalResults: Int,
    @Json(name = "total_pages") val totalPages: Int
)