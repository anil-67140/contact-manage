package com.deora.cms_app.database

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Contact::class], version = 1)
abstract class AppDataBase :RoomDatabase(){
    abstract fun contactDao():ContactDao

    companion object{
        @Volatile
       private var INSTANCE:AppDataBase?=null
        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_database.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}