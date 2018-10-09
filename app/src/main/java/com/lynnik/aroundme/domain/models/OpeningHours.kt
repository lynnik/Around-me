package com.lynnik.aroundme.domain.models

import com.google.gson.annotations.SerializedName

data class OpeningHours(
        @SerializedName("open_now") val openNow: Boolean?
)