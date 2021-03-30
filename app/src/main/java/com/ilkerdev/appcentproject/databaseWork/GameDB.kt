package com.ilkerdev.appcentproject.databaseWork

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//Begenilen oyunlari iceren local veritabaninin tanımlandigi kotlin sinifi.

@Database(entities = [DbItem::class], version = 1)
abstract class GameDB : RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {
        private var instance: GameDB? = null

        fun getLikedGamesDB(context: Context): GameDB? {

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    GameDB::class.java,
                    "GAME_DB.db"
                ).allowMainThreadQueries()
                    .build()
            }
            return instance
        }
    }
}