package com.jooheon.dday.data.database

import com.jooheon.dday.domain.model.Dday
import com.jooheon.dday.domain.toBoolean

fun DdayEntity.toDday(): Dday {
    return Dday(
        id = id,
        title = title,
        emoji = emoji,
        annualEvent = annualEvent.toBoolean(),
        selected = selected.toBoolean(),
        date = date
    )
}