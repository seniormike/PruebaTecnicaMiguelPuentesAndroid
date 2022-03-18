package com.mapr.credibanco.model.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DataArtist::class], version = 1, exportSchema = false
)

abstract class DaoDatabase : RoomDatabase() {
    abstract fun dataArtistDao(): DataArtistDao
}