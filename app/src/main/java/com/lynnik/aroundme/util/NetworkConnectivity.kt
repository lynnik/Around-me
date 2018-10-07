package com.lynnik.aroundme.util

import com.lynnik.aroundme.App
import com.lynnik.aroundme.data.error.NoInternetConnectionException
import com.lynnik.aroundme.extensions.hasNetworkConnection
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

object NetworkConnectivity {

    fun getStateFlowable(): Flowable<Boolean> = Flowable.create({ emitter ->
        if (App.applicationContext().hasNetworkConnection()) {
            emitter.onNext(true)
        } else {
            emitter.onError(NoInternetConnectionException())
        }
        emitter.onComplete()
    }, BackpressureStrategy.LATEST)
}