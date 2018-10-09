package com.lynnik.aroundme.domain

import com.lynnik.aroundme.data.Api
import com.lynnik.aroundme.data.RemoteDataStore

object Repository : Api {

    private val remoteDataStore = RemoteDataStore

    override fun placesNearMe(location: String, radius: Int, type: String, key: String) =
            remoteDataStore.placesNearMe(location, radius, type, key)
}