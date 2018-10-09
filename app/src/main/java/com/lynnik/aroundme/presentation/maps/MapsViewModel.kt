package com.lynnik.aroundme.presentation.maps

import android.location.Location
import androidx.lifecycle.MutableLiveData
import com.lynnik.aroundme.data.error.ErrorHandler
import com.lynnik.aroundme.domain.models.PlacesResponse
import com.lynnik.aroundme.extensions.bind
import com.lynnik.aroundme.presentation.base.BaseViewModel
import com.lynnik.aroundme.util.NetworkConnectivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer

class MapsViewModel : BaseViewModel() {

    val placesResponse = MutableLiveData<PlacesResponse>()

    fun placesNearMe(location: Location) {
        val currentLocation = "${location.latitude},${location.longitude}"
        val radius = 500
        val type = "store"
        val apiKey = "AIzaSyCfCs2aYb8-84mfCM9eCFWoW3mnmzF7N-4"

        NetworkConnectivity.getStateFlowable()
                .flatMap { repository.placesNearMe(currentLocation, radius, type, apiKey) }
                .doOnSubscribe { showLoading() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        Consumer { onGetPlacesNearMe(it) },
                        ErrorHandler {
                            hideLoading()
                            showError(it.message.toString())
                        }
                ).bind(compositeDisposable)
    }

    private fun onGetPlacesNearMe(response: PlacesResponse) {
        placesResponse.postValue(response)
        hideLoading()
    }
}