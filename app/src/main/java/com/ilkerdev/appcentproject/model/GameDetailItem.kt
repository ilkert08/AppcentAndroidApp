package com.ilkerdev.appcentproject.model

//Detay sayfasindaki oyun  detaylarini iceren data sinifi.

data class GameDetailItem(
    val id: Long,
    val name: String,
    val released: String,
    val background_image: String,
    val metacritic: Long,
    val description: String
)