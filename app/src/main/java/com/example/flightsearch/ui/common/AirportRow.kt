package com.example.flightsearch.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearch.R
import com.example.flightsearch.ui.theme.FlightSearchTheme

@Composable
fun AirportRow(
    airportCode: String,
    airportName: String,
    flightDirection: FlightDirection,
    modifier: Modifier = Modifier
) {

    val tintColor = if(flightDirection == FlightDirection.DEPARTURE)
        MaterialTheme.colorScheme.surfaceTint
    else MaterialTheme.colorScheme.secondary

    val backgroundTintColor = if(flightDirection == FlightDirection.DEPARTURE)
        MaterialTheme.colorScheme.primaryContainer
    else MaterialTheme.colorScheme.secondaryContainer



    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        modifier = modifier.padding(
            top = dimensionResource(R.dimen.padding_small),
            bottom = dimensionResource(R.dimen.padding_small),
            start = dimensionResource(R.dimen.padding_small)
        )
    ) {
        Icon(
            painter = painterResource(
                if(flightDirection == FlightDirection.DEPARTURE)
                    R.drawable.departure_icon
                else
                    R.drawable.arrival_icon),
            tint = tintColor
            ,
            contentDescription = null
        )
        Box(
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .clip(shape = RoundedCornerShape(8.dp))
                .background(color = backgroundTintColor)
                .padding(dimensionResource(R.dimen.padding_small))


        ) {
            Text(
                text = airportCode,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.surface
            )
        }


        Text(
            text = airportName,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AirportRowPreview() {
    FlightSearchTheme {
        Column(
            horizontalAlignment = AbsoluteAlignment.Left,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AirportRow(
                airportCode = "JFK",
                airportName = "John F. Kennedy International Airport",
                flightDirection = FlightDirection.DEPARTURE
            )
            AirportRow(
                airportCode = "III",
                airportName = "Ivato International Airport",
                flightDirection = FlightDirection.ARRIVAL
            )
        }
    }
}

