package com.prmto.inviostaj.domain.usecase

class ConvertVoteCountToKFormatUseCase {
    operator fun invoke(voteCount: Int): String {
        if (voteCount < 1000)
            return voteCount.toString()

        val voteCountText: Int?

        val divide = (voteCount / 1000).toFloat()

        val mod = voteCount % 1000

        if (mod > 100) {
            voteCountText = (mod / 100)
        } else {
            return "${divide.toInt()} k"
        }

        return "${divide.toInt()}.$voteCountText k"
    }
}