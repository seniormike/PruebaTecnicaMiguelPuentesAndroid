package com.mapr.credibanco.model.artists

import com.mapr.credibanco.model.ImageOBJ

data class ArtistOBJ(
    val name: String,
    val listeners: String,
    val mbid: String,
    val url: String,
    val streamable: String,
    val image: List<ImageOBJ>
)