package com.prmto.inviostaj.domain.usecase

import com.prmto.inviostaj.util.Constants.HOUR_KEY
import com.prmto.inviostaj.util.Constants.MINUTES_KEY

class ConvertRuntimeAsHourAndMinutesUseCase {

    operator fun invoke(runtime: Int?): Map<String, String> {
        runtime?.let {
            val hour = runtime / 60
            val minutes = (runtime % 60)
            return mapOf(
                HOUR_KEY to hour.toString(),
                MINUTES_KEY to minutes.toString()
            )
        } ?: return emptyMap()
    }
}