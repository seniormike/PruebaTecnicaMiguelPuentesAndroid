package com.mapr.credibanco.model.tracks

import com.mapr.credibanco.model.ImageOBJ

data class TrackOBJ(
    val name: String,
    val playcount: String,
    val listeners: String,
    val mbid: String,
    val url: String,
    val streamable: String,
    val image: List<ImageOBJ>
)