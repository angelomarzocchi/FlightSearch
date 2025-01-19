package com.example.flightsearch.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.flightsearch.R
import com.example.flightsearch.data.entity.FavoriteRoute
import com.example.flightsearch.ui.theme.FlightSearchTheme

@Composable
fun FavoriteRouteCard(
    departureCode: String,
    departureName: String,
    destinationCode: String,
    destinationName: String,
    onFavoriteClick: () -> Unit ,
    isFavoriteSelected: Boolean = false,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.weight(1f)
            ) {
                AirportRow(
                    departureCode,
                    departureName,
                    FlightDirection.DEPARTURE
                )
                AirportRow(
                    destinationCode,
                    destinationName,
                    FlightDirection.ARRIVAL)
            }
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
            IconButton(onClick = onFavoriteClick) {
                Icon(imageVector = Icons.Filled.Star,
                    tint = if(isFavoriteSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inversePrimary,
                    contentDescription = null)
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteRouteCardPreview() {
    val favoriteRoute = FavoriteRoute(
        favoriteId = 1,
        departureCode = "LAX",
        departureName = "John F. Kennedy International Airport",
        destinationCode = "LAX",
        destinationName = "Los Angeles International Airport"
    )
    FlightSearchTheme {
        FavoriteRouteCard(
            departureCode = favoriteRoute.departureCode,
            departureName = favoriteRoute.departureName,
            destinationCode = favoriteRoute.destinationCode,
            destinationName = favoriteRoute.destinationName,
            isFavoriteSelected = false, onFavoriteClick = {}, modifier = Modifier)
    }
}