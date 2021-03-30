package com.ilkerdev.appcentproject.databaseWork

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

//Veritabani sorgularini iceren interface.
//Bu veritabani begenilen oyunlari yerel cihazda tutmak icin olusturulur.

@Dao
interface GameDao {

    @Insert
    fun insert(game: DbItem)

    @Delete
    fun delete(game: DbItem)

    @Query("SELECT*FROM likedGamesTable")
    fun getAllLikedGames(): List<DbItem>
}
