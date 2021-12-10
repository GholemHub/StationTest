package com.example.stationstest.model

data class StationDataItem(
    val addressStreet: String,
    val city: City,
    val gegrLat: String,
    val gegrLon: String,
    val id: Int,
    val stationName: String
)