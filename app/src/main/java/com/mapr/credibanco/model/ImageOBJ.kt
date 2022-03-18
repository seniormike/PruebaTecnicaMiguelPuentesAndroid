package com.mapr.credibanco.model

import com.google.gson.annotations.SerializedName

data class ImageOBJ (
    @SerializedName("#text")
    val text: String,
    val size: String
)