package com.lynnik.aroundme.data.error

import com.lynnik.aroundme.App
import com.lynnik.aroundme.R

class NoInternetConnectionException
    : Exception(App.applicationContext().getString(R.string.exception_no_internet_connection))