package com.prmto.inviostaj.di

import android.content.Context
import androidx.room.Room
import com.prmto.inviostaj.data.local.InvioDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestDataModule {
    @Provides
    @Named("test_db")
    @Singleton
    fun provideInvioDatabase(
        @ApplicationContext context: Context
    ): InvioDatabase {
        return Room.inMemoryDatabaseBuilder(
            context,
            InvioDatabase::class.java
        ).build()
    }
}