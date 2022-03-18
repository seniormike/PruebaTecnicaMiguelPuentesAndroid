package com.mapr.credibanco.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DataArtist(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String = "",
    var listeners: String = "",
    var mbid: String = "",
    var url: String = "",
    var streamable: String = "",
    var imageUrl: String = ""
)