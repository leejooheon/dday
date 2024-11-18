package com.jooheon.dday.data.database

import com.jooheon.dday.domain.model.Dday
import com.jooheon.dday.domain.model.DdayDataSource
import com.jooheon.dday.domain.toLong

class SqlDelightDdayDataSource(
    db: DdayDatabase
): DdayDataSource {
    private val queries = db.ddayQueries

    override suspend fun insertDday(dday: Dday) {
        queries.insertDday(
            id = dday.id,
            title = dday.title,
            emoji = dday.emoji,
            annualEvent = dday.annualEvent.toLong(),
            selected = dday.selected.toLong(),
            date = dday.date
        )
    }

    override suspend fun getDdayById(id: Long): Dday? {
        return queries
            .getDdayById(id)
            .executeAsOneOrNull()
            ?.toDday()
    }

    override suspend fun getAllDdays(): List<Dday> {
        return queries
            .getAllDdays()
            .executeAsList()
            .map { it.toDday() }
    }

    override suspend fun deleteDdayById(id: Long) {
        queries.deleteDdayById(id)
    }
}