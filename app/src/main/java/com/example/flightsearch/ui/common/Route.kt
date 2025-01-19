package com.example.flightsearch.ui.common

import com.example.flightsearch.data.entity.Airport

data class Route(
    val departureAirport: Airport,
    val arrivalAirport: Airport,
    var isSelected: Boolean
)
