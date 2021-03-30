package com.ilkerdev.appcentproject.databaseWork

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Veritabani tablosunun tanimlandigi data class.
//Bu veritabani begenilen oyunlari yerel cihazda tutmak icin olusturulur.

@Entity(tableName = "likedGamesTable")
data class DbItem(
        @PrimaryKey()
        @ColumnInfo(name = "gameId")
        var gameId: String = ""
)