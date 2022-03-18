package com.mapr.credibanco.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mapr.credibanco.application.AppMusicapp
import com.mapr.credibanco.model.db.DataArtist
import com.mapr.credibanco.repositories.ArtistsRepository
import com.mapr.credibanco.services.requests.RequestTopArtists
import com.mapr.credibanco.services.responses.ResponseTopArtists
import com.mapr.credibanco.tools.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArtistsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ArtistsRepository.getInstance(application)

    /**
     * LiveData properties of artists list.
     */
    private var _artistsList = MutableLiveData<ArrayList<DataArtist>>()
    var artistsList: LiveData<ArrayList<DataArtist>> = _artistsList

    /**
     * LiveData properties of artists list.
     */
    private var _dialogMsg = MutableLiveData<String>()
    var dialogMsg: LiveData<String> = _dialogMsg
    /**
     *
     */
    fun requestTopArtists(
        auth: String,
        country: String,
        request: RequestTopArtists,
    ) {
        repository.requestTopArtists(auth, country, request, object: ArtistsRepository.OnListenerResponseTopArtists {
            override fun onResponseTopArtists(responseTopArtists: ResponseTopArtists) {
                val list = ArrayList<DataArtist>()
                responseTopArtists.topartists.artist.forEach {
                    val dataArtist = DataArtist()
                    dataArtist.name = it.name
                    dataArtist.listeners = it.listeners
                    dataArtist.mbid = it.mbid
                    dataArtist.url = it.url
                    dataArtist.streamable = it.streamable
                    if (it.image.isNotEmpty()) {
                        dataArtist.imageUrl = it.image[0].size
                    } else {
                        dataArtist.imageUrl = ""
                    }
                    list.add(dataArtist)
                }
                _artistsList.postValue(list)
            }

            override fun onFailedTopArtists() {
                Utils().printLog("On failure")
            }
        })
    }

    fun getArtistsInDb(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val list = ArrayList(AppMusicapp(context).dataArtistDao().getAll())
            _artistsList.postValue(list)
        }
    }
}