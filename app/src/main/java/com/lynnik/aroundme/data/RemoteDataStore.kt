package com.lynnik.aroundme.data

object RemoteDataStore : Api {

    private val api: Api by lazy { RetrofitCreator.initApi() }
}