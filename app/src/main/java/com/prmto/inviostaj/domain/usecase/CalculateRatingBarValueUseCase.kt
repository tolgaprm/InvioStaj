package com.prmto.inviostaj.domain.usecase

class CalculateRatingBarValueUseCase {
    operator fun invoke(voteAverage: Double): Float {
        return ((voteAverage * 5) / 10).toFloat()
    }
}