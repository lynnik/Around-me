package com.lynnik.aroundme.data

import com.lynnik.aroundme.domain.models.PlacesResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("/maps/api/place/nearbysearch/json")
    fun placesNearMe(
            @Query("location") location: String,
            @Query("radius") radius: Int,
            @Query("type") type: String,
            @Query("key") key: String
    ): Flowable<PlacesResponse>
}