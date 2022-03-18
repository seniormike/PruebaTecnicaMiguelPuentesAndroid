package com.mapr.credibanco.repositories

import android.app.Application
import com.google.gson.Gson
import com.mapr.credibanco.services.ApiServices
import com.mapr.credibanco.services.requests.RequestArtistTracks
import com.mapr.credibanco.services.responses.ResponseArtistTracks
import com.mapr.credibanco.tools.Constants
import com.mapr.credibanco.tools.Constants.BASE_URL
import com.mapr.credibanco.tools.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TracksRepository(private val application: Application) {

    private var apiServices: ApiServices = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(ApiServices::class.java)

    fun requestTopArtistTracks(
        auth: String,
        mbid: String,
        requestArtistTracks: RequestArtistTracks,
        listener: OnListenerResponseTopTracks
    ) {
        Utils().printLog("Request: " + Gson().toJson(requestArtistTracks))

        val call: Call<ResponseArtistTracks> =
            apiServices.requestTopArtistTracks(
                auth,
                Constants.METHOD_GET_TRACKS,
                mbid,
                Constants.FORMAT_JSON,
                Constants.LIMIT_NUM_TRACKS,
                requestArtistTracks
            )
        call.enqueue(object : Callback<ResponseArtistTracks> {
            override fun onResponse(
                call: Call<ResponseArtistTracks>,
                response: Response<ResponseArtistTracks>
            ) {
                val responseObj: ResponseArtistTracks = response.body()!!
                Utils().printLog("Response: " + Gson().toJson(responseObj))

                if (response.code() == Constants.SUCCESS_STATUS) {
                    listener.onResponseTopArtistTracks(responseObj)

                } else {
                    listener.onFailedTopTracks()
                }
            }

            override fun onFailure(call: Call<ResponseArtistTracks>, t: Throwable) {
                Utils().printLog(call.toString())
                Utils().printLog(t.message.toString())
                listener.onFailedTopTracks()
            }
        })
    }

    /**
     *
     */
    interface OnListenerResponseTopTracks {
        fun onResponseTopArtistTracks(responseArtistTracks: ResponseArtistTracks)
        fun onFailedTopTracks()
    }

    companion object {
        @Volatile
        private var instance: TracksRepository? = null
        private val LOCK = Any()
        fun getInstance(context: Application) = instance ?: synchronized(LOCK) {
            instance ?: TracksRepository(context).also { instance = it }
        }
    }
}