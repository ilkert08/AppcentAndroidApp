package com.ilkerdev.appcentproject.model

//Oyunlari ceken api isteginden donen kisimlari iceren data sinifi.
//Burada sadece ResultsItem sinifi apiden alinmistir.


data class GameItem(
    val results: List <ResultsItem>
)