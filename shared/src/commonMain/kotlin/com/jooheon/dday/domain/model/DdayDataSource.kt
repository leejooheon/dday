package com.jooheon.dday.domain.model

interface DdayDataSource {
    suspend fun insertDday(dday: Dday)
    suspend fun getDdayById(id: Long): Dday?
    suspend fun getAllDdays(): List<Dday>
    suspend fun deleteDdayById(id: Long)
}