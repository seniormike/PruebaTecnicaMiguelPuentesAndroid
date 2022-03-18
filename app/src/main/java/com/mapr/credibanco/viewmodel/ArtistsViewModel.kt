package com.mapr.credibanco.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mapr.credibanco.application.AppMusicapp
import com.mapr.credibanco.model.db.DataArtist
import com.mapr.credibanco.model.tracks.TrackOBJ
import com.mapr.credibanco.repositories.ArtistsRepository
import com.mapr.credibanco.repositories.TracksRepository
import com.mapr.credibanco.services.requests.RequestArtistTracks
import com.mapr.credibanco.services.requests.RequestTopArtists
import com.mapr.credibanco.services.responses.ResponseArtistTracks
import com.mapr.credibanco.services.responses.ResponseTopArtists
import com.mapr.credibanco.tools.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArtistsViewModel(application: Application) : AndroidViewModel(application) {

    private val artistRepository = ArtistsRepository.getInstance(application)
    private val tracksRepository = TracksRepository.getInstance(application)

    /**
     * LiveData properties of artists list.
     */
    private var _artistsList = MutableLiveData<ArrayList<DataArtist>>()
    var artistsList: LiveData<ArrayList<DataArtist>> = _artistsList

    /**
     * LiveData properties of artists list.
     */
    private var _tracksList = MutableLiveData<ArrayList<TrackOBJ>>()
    var tracksList: LiveData<ArrayList<TrackOBJ>> = _tracksList

    /**
     * LiveData properties of artists list.
     */
    private var _dialogMsg = MutableLiveData<String>()
    var dialogMsg: LiveData<String> = _dialogMsg

    /**
     * Method that calls top artists service.
     */
    fun requestTopArtists(
        auth: String,
        country: String,
        request: RequestTopArtists,
    ) {
        artistRepository.requestTopArtists(
            auth,
            country,
            request,
            object : ArtistsRepository.OnListenerResponseTopArtists {
                override fun onResponseTopArtists(responseTopArtists: ResponseTopArtists) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val list = ArrayList(AppMusicapp(getApplication()).dataArtistDao().getAll())
                        _artistsList.postValue(list)
                        _dialogMsg.postValue("")
                    }
                }

                override fun onFailedTopArtists() {
                    Utils().printLog("On failure")
                    _dialogMsg.postValue("Oops! Revisa tu conexión e intenta de nuevo.")
                }
            })
    }

    fun deleteArtistDB(item: DataArtist) {
        CoroutineScope(Dispatchers.IO).launch {
            val delete = AppMusicapp(getApplication()).dataArtistDao().delete(item)
            val list = ArrayList(AppMusicapp(getApplication()).dataArtistDao().getAll())
            _artistsList.postValue(list)
        }
    }

    /**
     *
     */
    fun requestTopTracks(
        auth: String,
        mbid: String,
        request: RequestArtistTracks,
    ) {
        tracksRepository.requestTopArtistTracks(
            auth,
            mbid,
            request,
            object : TracksRepository.OnListenerResponseTopTracks {
                override fun onResponseTopArtistTracks(responseArtistTracks: ResponseArtistTracks) {
                    val list = ArrayList(responseArtistTracks.toptracks.track)
                    _tracksList.postValue(list)
                    _dialogMsg.postValue("")
                }

                override fun onFailedTopTracks() {
                    Utils().printLog("On failure")
                    _dialogMsg.postValue("Oops! Revisa tu conexión e intenta de nuevo.")
                }
            })
    }
}