package com.example.flightsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearch.data.entity.FavoriteRoute
import com.example.flightsearch.data.repository.FavoriteRouteRepository
import com.example.flightsearch.ui.common.Route
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch



class FavoriteRouteViewModel(
   private val favoriteRouteRepository: FavoriteRouteRepository
): ViewModel() {





}