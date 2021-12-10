package com.example.stationstest.interfaces

import com.example.stationstest.model.StationDataItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("/pjp-api/rest/station/findAll")
    fun getData(): Call<List<StationDataItem>>
}