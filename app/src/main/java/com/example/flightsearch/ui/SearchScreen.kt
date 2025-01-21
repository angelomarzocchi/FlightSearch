package com.example.flightsearch.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.flightsearch.R
import com.example.flightsearch.data.entity.Airport
import com.example.flightsearch.ui.common.AirportRow
import com.example.flightsearch.ui.common.FavoriteRouteCard
import com.example.flightsearch.ui.common.FlightDirection
import com.example.flightsearch.ui.common.Route
import com.example.flightsearch.ui.navigation.NavigationDestination
import com.example.flightsearch.ui.theme.FlightSearchTheme

object SearchDestination: NavigationDestination {
    override val route: String = "search"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navigateBack: () -> Unit,
    paddingValues: PaddingValues,
    airportViewModel: AirportViewModel,
    modifier: Modifier = Modifier
) {
    val airportUiState = airportViewModel.airportUiState.collectAsState()
    val favoriteRouteUiState = airportViewModel.favoriteRouteUiState.collectAsState()
    val query by airportViewModel.query.collectAsState()
    val lazyListState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current

    val focusRequester = remember { FocusRequester() }


    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }



Column(
    modifier = modifier.padding(
        top =paddingValues.calculateTopPadding(),
        bottom = paddingValues.calculateBottomPadding(),
        start = dimensionResource(R.dimen.padding_small),
        end = dimensionResource(R.dimen.padding_small)
    )
) {
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium),

        inputField = {
            SearchBarDefaults.InputField(
                leadingIcon = {
                    IconButton(onClick = {
                        airportViewModel.initializeState()
                        keyboardController?.hide()
                        navigateBack()
                    }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back_to_home_screen))}
                    },
                placeholder = { Text(stringResource(R.string.search)) },
                query = query,
                onQueryChange = { airportViewModel.onQuery(query = it) },
                expanded = true,
                onExpandedChange = {},
                onSearch = {},
                trailingIcon = {Icon(imageVector = Icons.Filled.Search, contentDescription = null)},
                modifier = Modifier.focusRequester(focusRequester)
            )
        },
        expanded = true,
        onExpandedChange = {},

        ){
        SearchBarActiveScreen(
            airportUiState = airportUiState.value,
            favoriteRouteUiState = favoriteRouteUiState.value,
            onAirportClicked = {
                airportId -> airportViewModel.onDepartureAirportSelected(airportUiState.value.departureAirportList.first { it.id == airportId }) },
            onFavoriteClicked = { route->
                airportViewModel.onFavoriteRouteToggle(route)
                                },
            lazyListState = lazyListState
        )
    }
}

    BackHandler(onBack = {
        keyboardController?.hide()
        navigateBack()
    })





}


@Composable
fun SearchBarActiveScreen(
    airportUiState: AirportUiState,
    favoriteRouteUiState: FavoriteRouteUiState,
    lazyListState: LazyListState,
    onAirportClicked: (Int) -> Unit,
    onFavoriteClicked: (Route) -> Unit,
    modifier: Modifier = Modifier
) {

    val selectAirportStringResource =
        if(!airportUiState.isAirportSelected)
            R.string.select_departure_airport
        else R.string.select_arrival_airport

    val iconResource =
        if(!airportUiState.isAirportSelected)
            R.drawable.departure_icon
    else R.drawable.arrival_icon

    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(all = dimensionResource(R.dimen.padding_small))
        ) {
            Icon(
                painter = painterResource(
                    iconResource),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
            Text(
                text = stringResource(selectAirportStringResource),
                style = MaterialTheme.typography.titleSmall,
            )

        }

        if(!airportUiState.isAirportSelected)
        AirportListScreen(
            airports = airportUiState.departureAirportList,
            onItemClicked = onAirportClicked,
            lazyListState = lazyListState
        )
        else AirportRoutesScreen(
            routes = favoriteRouteUiState.potentialRoutes,
            lazyListState = lazyListState,
            onItemClicked = { route ->
                onFavoriteClicked(route)
            }
        )
    }
}

@Composable
fun AirportRoutesScreen(
    routes: List<Route>,
    lazyListState: LazyListState,
    onItemClicked: (Route) -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val isAtTop by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0 && lazyListState.firstVisibleItemScrollOffset == 0
        }
    }


    LaunchedEffect(key1 = isAtTop) {
        if (isAtTop) {
            keyboardController?.show()
        } else {
            keyboardController?.hide()
        }
    }


    LazyColumn(
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(dimensionResource( R.dimen.padding_small)),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
    ) {
        items(items = routes, key = { route -> route.arrivalAirport.id }) { route ->
            val isItemLast = route == routes.last()
            FavoriteRouteCard(
                departureCode = route.departureAirport.code,
                departureName = route.departureAirport.name,
                destinationCode = route.arrivalAirport.code,
                destinationName = route.arrivalAirport.name,
                isFavoriteSelected = route.isSelected,
                onFavoriteClick = { onItemClicked(route) },
                modifier = if(isItemLast) Modifier.padding(bottom = dimensionResource(R.dimen.padding_small)) else Modifier
            )
        }
    }

}

@Composable
fun AirportListScreen(
    airports: List<Airport>,
    lazyListState:LazyListState,
    onItemClicked: (Int) -> Unit,
    modifier: Modifier = Modifier) {

    val keyboardController = LocalSoftwareKeyboardController.current


    val isAtTop by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0 && lazyListState.firstVisibleItemScrollOffset == 0
        }
    }


    LaunchedEffect(key1 = isAtTop) {
        if (isAtTop) {
            keyboardController?.show()
        } else {
            keyboardController?.hide()
        }
    }



    LazyColumn(
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(dimensionResource( R.dimen.padding_small)),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
    ) {
        items(items = airports, key = { airport -> airport.id }) { airport ->
            val isItemLast = airport == airports.last()
            AirportCard(
                airport = airport,
                flightDirection = FlightDirection.DEPARTURE ,
                onClick = { onItemClicked(airport.id) },
                modifier = if(isItemLast) Modifier.padding(bottom = dimensionResource(R.dimen.padding_small)) else Modifier)
        }
    }
}

@Composable
fun AirportCard(
    airport: Airport,
    flightDirection: FlightDirection,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        onClick = { onClick() }

        ) {
        AirportRow(
            airportCode = airport.code,
            airportName = airport.name,
            flightDirection = flightDirection
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AirportListScreenPreview() {
    val airport1 = Airport(
        id = 1,
        code = "JFK",
        name = "John F. Kennedy International Airport",
        passengers = 2000
    )

    val airport2 = Airport(
        id = 2,
        code = "JFK",
        name = "John F. Kennedy International Airport",
        passengers = 2000
    )

    val airport3 = Airport(
        id = 3,
        code = "JFK",
        name = "John F. Kennedy International Airport",
        passengers = 2000
    )

    val airports = listOf(airport1, airport2, airport3)
    FlightSearchTheme {
        AirportListScreen(airports = airports, lazyListState = rememberLazyListState(),onItemClicked = {})
    }

}

@Preview(showBackground = true)
@Composable
fun AirportCardPreview() {
    val airport = Airport(
        id = 1,
        code = "JFK",
        name = "John F. Kennedy International Airport",
        passengers = 2000
    )
    FlightSearchTheme {
        AirportCard(airport = airport, flightDirection = FlightDirection.ARRIVAL, onClick = {})
    }
}