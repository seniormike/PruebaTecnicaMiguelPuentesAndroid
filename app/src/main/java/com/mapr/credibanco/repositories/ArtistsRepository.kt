package com.mapr.credibanco.repositories

import android.app.Application
import com.google.gson.Gson
import com.mapr.credibanco.application.AppMusicapp
import com.mapr.credibanco.model.db.DataArtist
import com.mapr.credibanco.services.ApiServices
import com.mapr.credibanco.services.requests.RequestTopArtists
import com.mapr.credibanco.services.responses.ResponseTopArtists
import com.mapr.credibanco.tools.Constants
import com.mapr.credibanco.tools.Constants.BASE_URL
import com.mapr.credibanco.tools.Utils
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArtistsRepository(private val application: Application) {

    private var apiServices: ApiServices = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(ApiServices::class.java)

    fun requestTopArtists(
        auth: String,
        country: String,
        requestTopArtists: RequestTopArtists,
        listener: OnListenerResponseTopArtists
    ) {
        Utils().printLog("Request: " + Gson().toJson(requestTopArtists))
        val call: Call<ResponseTopArtists> =
            apiServices.requestTopArtists(
                auth,
                Constants.METHOD_GET_ARTISTS,
                country,
                Constants.FORMAT_JSON,
                Constants.LIMIT_NUM_ARTISTS,
                requestTopArtists
            )
        call.enqueue(object : Callback<ResponseTopArtists> {
            override fun onResponse(
                call: Call<ResponseTopArtists>,
                response: Response<ResponseTopArtists>
            ) {
                val responseObj: ResponseTopArtists = response.body()!!
                Utils().printLog(responseObj.toString())
                Utils().printLog("Response: " + Gson().toJson(responseObj))

                if (response.code() == Constants.SUCCESS_STATUS) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val list = ArrayList<DataArtist>()
                        responseObj.topartists.artist.forEach {
                            val dataArtist = DataArtist()
                            dataArtist.name = it.name
                            dataArtist.listeners = it.listeners
                            dataArtist.mbid = it.mbid
                            dataArtist.url = it.url
                            dataArtist.streamable = it.streamable
                            if (it.image.isNotEmpty()) {
                                dataArtist.imageUrl = it.image[0].text
                            } else {
                                dataArtist.imageUrl = ""
                            }
                            list.add(dataArtist)
                        }
                        AppMusicapp(application).dataArtistDao().deleteAll()
                        val insert = AppMusicapp(application).dataArtistDao().insertAll(list)
                        Utils().printLog("Insertando en la BD Artist $insert")
                        listener.onResponseTopArtists(responseObj)
                    }
                } else {
                    listener.onFailedTopArtists()
                }
            }

            override fun onFailure(call: Call<ResponseTopArtists>, t: Throwable) {
                Utils().printLog(call.toString())
                Utils().printLog(t.message.toString())
                listener.onFailedTopArtists()
            }
        })
    }

    /**
     * Listener para responder el servicio de top artist
     */
    interface OnListenerResponseTopArtists {
        fun onResponseTopArtists(responseTopArtists: ResponseTopArtists)
        fun onFailedTopArtists()
    }

    companion object {
        @Volatile
        private var instance: ArtistsRepository? = null
        private val LOCK = Any()
        fun getInstance(context: Application) = instance ?: synchronized(LOCK) {
            instance ?: ArtistsRepository(context).also { instance = it }
        }
    }
}