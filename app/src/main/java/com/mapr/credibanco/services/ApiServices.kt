package com.mapr.credibanco.services

import com.mapr.credibanco.services.requests.RequestTopArtists
import com.mapr.credibanco.services.requests.RequestArtistTracks
import com.mapr.credibanco.services.responses.ResponseTopArtists
import com.mapr.credibanco.services.responses.ResponseArtistTracks
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @POST(".")
    @Headers("Accept:Application/json")
    fun requestTopArtists(
        @Query("api_key") auth: String,
        @Query("method") method: String,
        @Query("country") country: String,
        @Query("format") format: String,
        @Query("limit") limit: Int,
        @Body data: RequestTopArtists
    ): Call<ResponseTopArtists>

    @POST(".")
    @Headers("Accept:Application/json")
    fun requestTopArtistTracks(
        @Query("api_key") auth: String,
        @Query("method") method: String,
        @Query("mbid") mbid: String,
        @Query("format") format: String,
        @Query("limit") limit: Int,
        @Body data: RequestArtistTracks
    ): Call<ResponseArtistTracks>
}