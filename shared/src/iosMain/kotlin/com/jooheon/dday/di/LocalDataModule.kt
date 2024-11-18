package com.jooheon.dday.di

import com.jooheon.dday.data.database.DatabaseDriverFactory
import com.jooheon.dday.data.database.DdayDatabase
import com.jooheon.dday.data.database.DdayEntity
import com.jooheon.dday.data.database.LongColumnAdapter
import com.jooheon.dday.data.database.SqlDelightDdayDataSource
import com.jooheon.dday.data.setting.SettingDataStore
import com.jooheon.dday.domain.model.DdayDataSource

class LocalDataModule {
    private val factory by lazy { DatabaseDriverFactory() }
    val ddayDataSource: DdayDataSource by lazy {
        val driver = DatabaseDriverFactory().createDriver()
        SqlDelightDdayDataSource(DdayDatabase(driver, DdayEntity.Adapter(LongColumnAdapter)))
    }

    val settingUserDefault = SettingDataStore()
}