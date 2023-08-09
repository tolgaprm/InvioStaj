package com.prmto.inviostaj.domain.util

import com.google.common.truth.Truth.assertThat
import com.prmto.inviostaj.util.Constants
import org.junit.Test

class MovieUtilsTest {
    @Test
    fun `calculateRatingBarValue should return valid rating`() {
        val voteAverage = 5.0
        val expected = 2.5f
        assertThat(MovieUtils.calculateRatingBarValue(voteAverage)).isEqualTo(expected)
    }

    @Test
    fun `convertRuntimeAsHourAndMinutes should return valid values for a given runtime`() {
        val result = MovieUtils.convertRuntimeAsHourAndMinutes(150)
        val expected = mapOf(Constants.HOUR_KEY to "2", Constants.MINUTES_KEY to "30")
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `convertRuntimeAsHourAndMinutes should return an empty map for null input`() {
        val result = MovieUtils.convertRuntimeAsHourAndMinutes(null)
        val expected = emptyMap<String, String>()
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `convertRuntimeAsHourAndMinutes should handle minutes-only runtime`() {
        val result = MovieUtils.convertRuntimeAsHourAndMinutes(45)
        val expected = mapOf(Constants.HOUR_KEY to "0", Constants.MINUTES_KEY to "45")
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `formatVoteCount should return the same value if vote count is below 1000`() {
        val result = MovieUtils.formatVoteCount(500)
        assertThat(result).isEqualTo("500")
    }

    @Test
    fun `formatVoteCount should format vote count between 1000 and 9999 as thousands with one decimal point`() {
        val result = MovieUtils.formatVoteCount(5400)
        assertThat(result).isEqualTo("5.4k")
    }

    @Test
    fun `formatVoteCount should format vote count 10000 and above as thousands`() {
        val result = MovieUtils.formatVoteCount(12000)
        assertThat(result).isEqualTo("12k")
    }
}