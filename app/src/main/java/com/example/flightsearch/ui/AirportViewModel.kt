package com.example.flightsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearch.data.entity.Airport
import com.example.flightsearch.data.entity.FavoriteRoute
import com.example.flightsearch.data.repository.AirportRepository
import com.example.flightsearch.data.repository.FavoriteRouteRepository
import com.example.flightsearch.ui.common.FlightDirection
import com.example.flightsearch.ui.common.Route
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface PotentialAirport {
    data class DepartureAirport(val airport: Airport) : PotentialAirport
    object NoAirportSelected : PotentialAirport
}


data class FavoriteRouteUiState(
    val favoriteRouteList: List<FavoriteRoute> = listOf(),
    val potentialRoutes: List<Route> = listOf()
)

data class AirportUiState(
    val departureAirportList: List<Airport> = listOf(),
    val isAirportSelected: Boolean = false
)





class AirportViewModel(
     private val airportRepository: AirportRepository,
     private val favoriteRouteRepository: FavoriteRouteRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _departureAirport = MutableStateFlow<PotentialAirport>(PotentialAirport.NoAirportSelected)
    private val _favoriteRouteList: StateFlow<List<FavoriteRoute>> =
        favoriteRouteRepository.getAllFavoriteRoutes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )



    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private val _departureAirportList: StateFlow<List<Airport>> = _query
        .debounce(300L)
        .flatMapLatest { query ->
            if(isQueryValid(query)) {
                _departureAirport.value = PotentialAirport.NoAirportSelected
                airportRepository.getAirportByExactCodeOrName(query)
            }
            else flowOf(emptyList())
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    val airportUiState: StateFlow<AirportUiState> = combine(_departureAirport, _departureAirportList) {
        departureAirport, departureAirportList ->
        AirportUiState(
            departureAirportList = departureAirportList,
            isAirportSelected = departureAirport is PotentialAirport.DepartureAirport
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = AirportUiState()
    )



    @OptIn(ExperimentalCoroutinesApi::class)
     private val _arrivalAirportList: StateFlow<List<Airport>> = _departureAirport
        .flatMapLatest { potentialAirport ->
            if(potentialAirport is PotentialAirport.DepartureAirport)
                airportRepository.getArrivalAirportList(potentialAirport.airport.id)
            else flowOf(emptyList())
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )



    val favoriteRouteUiState: StateFlow<FavoriteRouteUiState> =
        combine(
            _departureAirport,
            _arrivalAirportList,
            _favoriteRouteList) {departureAirport, arrivalAirportList, favoriteRouteList ->

                FavoriteRouteUiState(
                    favoriteRouteList = favoriteRouteList,
                    potentialRoutes =
                    if(departureAirport is PotentialAirport.DepartureAirport)
                        computeListOfPotentialRoutes(departureAirport.airport,arrivalAirportList,favoriteRouteList)
                    else emptyList()
                )

    }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = FavoriteRouteUiState()
            )











    fun onFavoriteRouteToggle(route: Route) {
        if(route.isSelected)
            deleteFavoriteRoute(route.departureAirport.code, route.arrivalAirport.code)
        else insertFavoriteRoute(route.departureAirport.code, route.arrivalAirport.code)
    }

    fun deleteFavoriteRoute(favoriteRoute: FavoriteRoute) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteRouteRepository.deleteFavoriteRoute(favoriteRoute)
        }
    }


   private fun insertFavoriteRoute(departureCode: String, destinationCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteRouteRepository.insertFavoriteRoute(departureCode, destinationCode)
        }
    }

   private fun deleteFavoriteRoute(departureCode: String, destinationCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteRouteRepository.deleteFavoriteRoute(departureCode, destinationCode)
        }
    }


    fun onQuery(query: String) {
         _query.value = query
    }

    fun onDepartureAirportSelected(airport: Airport) {
        _departureAirport.value = PotentialAirport.DepartureAirport(airport)
    }

    fun initializeState() {
        _departureAirport.value = PotentialAirport.NoAirportSelected
        _query.value = ""
    }


    private fun isQueryValid(query: String): Boolean {
        return query.isNotBlank() && query.length >= 3
    }

    private fun computeListOfPotentialRoutes(
        departureAirport: Airport,
        arrivalAirportList: List<Airport>,
        favoriteRouteList: List<FavoriteRoute>
    ): List<Route> {
       return arrivalAirportList.map { arrivalAirport ->
            Route(
                departureAirport = departureAirport,
                arrivalAirport = arrivalAirport,
                isSelected = favoriteRouteList.any {
                    it.departureCode == departureAirport.code && it.destinationCode == arrivalAirport.code
                }
            )
        }
    }

}



