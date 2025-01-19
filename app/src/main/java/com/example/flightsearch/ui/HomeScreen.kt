package com.example.flightsearch.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.R
import com.example.flightsearch.data.entity.FavoriteRoute
import com.example.flightsearch.ui.common.FavoriteRouteCard
import com.example.flightsearch.ui.navigation.NavigationDestination
import com.example.flightsearch.ui.theme.FlightSearchTheme

object HomeDestination : NavigationDestination {
    override val route = "home"
}


@Composable
fun HomeScreen(
    onSearchClick : () -> Unit,
    paddingValues: PaddingValues,
    airportViewModel: AirportViewModel,
    modifier: Modifier = Modifier,
) {
    val favoriteRouteUiState = airportViewModel.favoriteRouteUiState.collectAsState()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(
            top = paddingValues.calculateTopPadding(),
            start = dimensionResource(R.dimen.padding_small),
            end = dimensionResource(R.dimen.padding_small)
        )
    ) {
        item {
            SearchBarButton(onClick = { onSearchClick() }, modifier = Modifier.fillMaxWidth())
        }

        items(
            favoriteRouteUiState.value.favoriteRouteList,
            key = { favoriteRoute -> favoriteRoute.favoriteId }) { favoriteRoute ->
            val isLastItem = favoriteRoute == favoriteRouteUiState.value.favoriteRouteList.last()
            FavoriteRouteCard(
                departureCode = favoriteRoute.departureCode,
                departureName = favoriteRoute.departureName,
                destinationCode = favoriteRoute.destinationCode,
                destinationName = favoriteRoute.destinationName,
                isFavoriteSelected = true,
                onFavoriteClick = { airportViewModel.deleteFavoriteRoute(favoriteRoute) },
                modifier = if(!isLastItem) Modifier.fillMaxWidth() else Modifier.fillMaxWidth().padding(bottom = paddingValues.calculateBottomPadding())

            )
        }
    }
}















@Composable
fun SearchBarButton(onClick: () -> Unit ,modifier: Modifier = Modifier) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier)
            Text(text = "Search")
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBarButtonPreview() {
    FlightSearchTheme {
        SearchBarButton(onClick = {})
    }
}






