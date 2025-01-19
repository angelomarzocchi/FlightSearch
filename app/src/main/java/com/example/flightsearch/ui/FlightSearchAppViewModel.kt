package com.example.flightsearch.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class FlightSearchAppViewModel: ViewModel() {

    val isSearchBarActive = mutableStateOf(false)

    fun toggleSearchBar(value: Boolean) {
        isSearchBarActive.value = value
    }

}