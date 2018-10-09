package com.lynnik.aroundme.presentation.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.crashlytics.android.Crashlytics
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lynnik.aroundme.R
import com.lynnik.aroundme.domain.models.PlacesResponse
import com.lynnik.aroundme.presentation.base.BaseActivity
import com.lynnik.redditclient.extensions.visibleOrGone
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_maps.*
import timber.log.Timber

class MapsActivity : BaseActivity<MapsViewModel>(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    override fun layoutResId() = R.layout.activity_maps

    override fun viewModelClass() = MapsViewModel::class.java

    override fun onChangeProgressBarVisibility(isVisible: Boolean) {
        progress_bar?.apply { visibleOrGone(isVisible) }
    }

    override fun onShowError(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.error)
            setMessage(message)
            create()
            show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onBindLiveData() {
        super.onBindLiveData()
        observe(viewModel.placesResponse, ::showPlaces)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if ((grantResults.isNotEmpty()
                                && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    currentLocation()
                } else {
                    finish()
                }
                return
            }
            else -> {
                finish()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                                Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    ActivityCompat.requestPermissions(this,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
                }
            } else {
                currentLocation()
            }
        } else {
            currentLocation()
        }
    }

    private fun showPlaces(places: PlacesResponse) {
        map.clear()
        places.results?.forEach {
            it?.geometry?.location?.apply {
                val latitude = lat ?: 0.0
                val longitude = lng ?: 0.0
                val currentLocation = LatLng(latitude, longitude)
                map.addMarker(MarkerOptions().position(currentLocation))
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun currentLocation() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                0,
                0f,
                locationListener
        )
    }

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            val currentLocation = LatLng(location.latitude, location.longitude)
            map.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
            viewModel.placesNearMe(location)
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            Timber.d("onStatusChanged provider: $provider, status: $status")
        }

        override fun onProviderEnabled(provider: String) {
            Timber.d("onProviderEnabled provider: $provider")
        }

        override fun onProviderDisabled(provider: String) {
            Timber.d("onProviderDisabled provider: $provider")
        }
    }

    companion object {
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 123
    }
}
