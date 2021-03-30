package com.ilkerdev.appcentproject.model

//Apiden donen results arayinin bilgilerini tutan data class.

data class ResultsItem(
    val id: Long,
    val name: String,
    val released: String,
    val background_image: String,
    val rating: Double
)