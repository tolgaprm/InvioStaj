package com.prmto.inviostaj.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.prmto.inviostaj.data.local.convertor.DatabaseConverter
import com.prmto.inviostaj.data.local.dao.MovieDao
import com.prmto.inviostaj.data.remote.dto.Movie

@Database(
    entities = [Movie::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DatabaseConverter::class)

abstract class InvioDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        const val DATABASE_NAME = "InvioDatabase"
    }
}