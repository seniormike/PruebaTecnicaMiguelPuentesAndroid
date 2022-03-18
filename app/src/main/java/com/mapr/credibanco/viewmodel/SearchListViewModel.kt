package com.mapr.credibanco.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mapr.credibanco.application.AppMusicapp
import com.mapr.credibanco.repositories.ArtistsRepository
import com.mapr.credibanco.services.requests.RequestArtistTracks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ArtistsRepository.getInstance(application)

/*    *//**
     * LiveData properties of products list.
     *//*
    private var _authList = MutableLiveData<ArrayList<DataAuthorization>>()
    var authList: LiveData<ArrayList<DataAuthorization>> = _authList

    fun getAuthInDb(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val list = ArrayList(AppMusicapp(context).dataAuthDao().getAll())
            _authList.postValue(list)
        }
    }

    *//**
     *
     *//*
    fun makeCancellation(
        auth: String,
        request: RequestArtistTracks,
        listener: ArtistsRepository.OnListenerResponseCancellation
    ) {
        repository.makeCancellationRequest(auth, request, listener)
    }

    *//**
     *
     *//*
    fun searchByReceiptId(
        context: Context,
        input: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val list = ArrayList(AppMusicapp(context).dataAuthDao().getAll())
            val filtered = ArrayList<DataAuthorization>()
            list.forEach {
                if (it.receiptId.startsWith(input)) {
                    filtered.add(it)
                }
            }
            _authList.postValue(filtered)
        }
    }*/
}