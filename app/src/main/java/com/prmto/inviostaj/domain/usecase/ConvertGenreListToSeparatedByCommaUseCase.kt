package com.prmto.inviostaj.domain.usecase

import com.prmto.inviostaj.data.remote.dto.Genre

class ConvertGenreListToSeparatedByCommaUseCase {

    operator fun invoke(
        genreIds: List<Int>,
        genreList: List<Genre>
    ): String {
        var genreNames = ""

        if (genreIds.isEmpty()) {
            return ""
        }

        genreList.forEach { genre ->
            genreIds.forEach {
                if (it == genre.id) {
                    genreNames += "${genre.name}, "
                }
            }
        }

        if (genreNames.isNotEmpty()) {
            return genreNames.subSequence(0, genreNames.length - 2).toString()
        }

        return genreNames
    }
}