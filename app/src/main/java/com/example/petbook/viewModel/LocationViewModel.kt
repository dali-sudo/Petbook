package com.example.petbook.viewModel

import androidx.lifecycle.ViewModel
import com.example.petbook.repository.MapsRepository

class LocationViewModel : ViewModel() {

    private val repo = MapsRepository()

    fun getNearByPlace(url: String) = repo.getPlaces(url)


}