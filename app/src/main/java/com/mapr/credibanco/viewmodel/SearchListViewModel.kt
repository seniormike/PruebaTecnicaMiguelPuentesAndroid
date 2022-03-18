package com.mapr.credibanco.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mapr.credibanco.repositories.ArtistsRepository

class SearchListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ArtistsRepository.getInstance(application)

}