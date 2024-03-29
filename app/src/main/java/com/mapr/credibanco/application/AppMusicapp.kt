package com.mapr.credibanco.application

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.mapr.credibanco.R
import com.mapr.credibanco.model.db.DaoDatabase

class AppMusicapp : Application() {
    companion object {
        private var instanceApp: AppMusicapp? = null
        fun getInstanceApp(): AppMusicapp {
            if (instanceApp == null) {
                instanceApp = AppMusicapp()
            }
            return instanceApp as AppMusicapp
        }

        /**
         * Se inicializa la sesión de base de datos como un singleton
         */
        @Volatile
        private var instance: DaoDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context, DaoDatabase::class.java, context.resources.getString(R.string.db_name)
        ).build()
    }
}