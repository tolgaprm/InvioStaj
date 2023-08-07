package com.prmto.inviostaj.domain.util

import com.prmto.inviostaj.util.Constants

class MovieUtils {
    companion object {
        fun calculateRatingBarValue(voteAverage: Double): Float {
            return ((voteAverage * 5) / 10).toFloat()
        }

        fun convertRuntimeAsHourAndMinutes(runtimeInMin: Int?): Map<String, String> {
            if (runtimeInMin == null) {
                return emptyMap()
            }
            val hour = runtimeInMin / 60
            val minutes = (runtimeInMin % 60)
            return mapOf(
                Constants.HOUR_KEY to hour.toString(),
                Constants.MINUTES_KEY to minutes.toString()
            )
        }

        fun formatVoteCount(voteCount: Int): String {
            return when {
                voteCount < 1000 -> voteCount.toString()
                voteCount < 10_000 -> "${(voteCount / 1000)}.${(voteCount % 1000 / 100)}k"
                else -> "${(voteCount / 1000)}k"
            }
        }
    }
}