package com.lynnik.aroundme.data

object RemoteDataStore : Api {

    private val api: Api by lazy { RetrofitCreator.initApi() }

    override fun placesNearMe(location: String, radius: Int, type: String, key: String) =
            api.placesNearMe(location, radius, type, key)
}