package com.jooheon.dday.android.di

import android.app.Application
import app.cash.sqldelight.db.SqlDriver
import com.jooheon.dday.data.database.DatabaseDriverFactory
import com.jooheon.dday.data.database.DdayDatabase
import com.jooheon.dday.data.database.DdayEntity
import com.jooheon.dday.data.database.LongColumnAdapter
import com.jooheon.dday.data.database.SqlDelightDdayDataSource
import com.jooheon.dday.data.setting.SettingDataStore
import com.jooheon.dday.domain.model.DdayDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).createDriver()
    }

    @Provides
    @Singleton
    fun provideNoteDataSource(driver: SqlDriver): DdayDataSource {
        val database = DdayDatabase(driver, DdayEntity.Adapter(LongColumnAdapter))
        return SqlDelightDdayDataSource(database)
    }

    @Provides
    fun provideDataStoreStorage(app: Application): SettingDataStore {
        return SettingDataStore(context = app)
    }
}