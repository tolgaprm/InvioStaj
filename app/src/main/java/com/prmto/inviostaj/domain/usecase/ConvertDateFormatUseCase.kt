package com.prmto.inviostaj.domain.usecase

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class ConvertDateFormatUseCase {
    operator fun invoke(inputDate: String?): String {
        if (inputDate.isNullOrEmpty()) {
            return ""
        }
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault())
        return try {
            val date = dateFormat.parse(inputDate)
            outputDateFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }
}