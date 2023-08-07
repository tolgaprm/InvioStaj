package com.prmto.inviostaj.data.remote.dto

data class Genre(
    val id: Int,
    val name: String
)

data class GenreList(
    val genres: List<Genre>
)