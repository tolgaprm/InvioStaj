package com.prmto.inviostaj.domain.usecase

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ConvertDateFormatUseCaseTest {
    private lateinit var convertDateFormatUseCase: ConvertDateFormatUseCase
    @Before
    fun setUp() {
        convertDateFormatUseCase = ConvertDateFormatUseCase()
    }

    @Test
    fun `when input date is null or empty then return empty string`() {
        val inputDate = ""
        val result = convertDateFormatUseCase(inputDate)
        assertThat(result).isEmpty()
    }

    @Test
    fun `when input date is null then return empty string`() {
        val inputDate = null
        val result = convertDateFormatUseCase(inputDate)
        assertThat(result).isEmpty()
    }

    @Test
    fun `when input date is valid then return formatted date`() {
        val inputDate = "2021-01-01"
        val result = convertDateFormatUseCase(inputDate)
        assertThat(result).isEqualTo("01 January, 2021")
    }
}