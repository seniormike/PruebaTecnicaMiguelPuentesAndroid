package com.mapr.credibanco.repositories

import android.app.Application
import com.google.gson.Gson
import com.mapr.credibanco.application.AppMusicapp
import com.mapr.credibanco.services.ApiServices
import com.mapr.credibanco.services.requests.RequestArtistTracks
import com.mapr.credibanco.services.responses.ResponseArtistTracks
import com.mapr.credibanco.tools.Constants
import com.mapr.credibanco.tools.Constants.BASE_URL
import com.mapr.credibanco.tools.Utils
import kotlinx.coroutines.*
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

    fun makeCancellationRequest(
        auth: String,
        requestArtistTracks: RequestArtistTracks,
        listener: OnListenerResponseCancellation
    ) {
        val authorization = "Basic ${Utils().convertToBase64(auth)}"
        Utils().printLog("AUTH: " + Gson().toJson(authorization))
        Utils().printLog("makeAuth Request: " + Gson().toJson(requestArtistTracks))

        val call: Call<ResponseArtistTracks> =
            apiServices.requestTopArtistTracks(authorization, requestArtistTracks)
        call.enqueue(object : Callback<ResponseArtistTracks> {
            override fun onResponse(
                call: Call<ResponseArtistTracks>,
                response: Response<ResponseArtistTracks>
            ) {
                val responseObj: ResponseArtistTracks = response.body()!!
                Utils().printLog("makeAuth Response: " + Gson().toJson(responseObj))

               /* if (responseObj.statusCode == Constants.SUCCESS_STATUS) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val dataTodb =AppMusicapp(application).dataAuthDao().findByReceiptId(requestArtistTracks.receiptId)
                        AppMusicapp(application).dataAuthDao().delete(dataTodb)
                        Utils().printLog("Eliminado en la BD Auth $dataTodb")
                    }
                }*/
                listener.onResponseCancellation(responseObj)
            }

            override fun onFailure(call: Call<ResponseArtistTracks>, t: Throwable) {
                Utils().printLog(call.toString())
                Utils().printLog(t.message.toString())
                listener.onFailedCancellation()
            }
        })
    }

    /**
     *
     */
    interface OnListenerResponseCancellation {
        fun onResponseCancellation(responseArtistTracks: ResponseArtistTracks)
        fun onFailedCancellation()
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