package com.example.flightsearch.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.flightsearch.R
import com.example.flightsearch.ui.navigation.FlightSearchNavHost


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchApp(
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { FlightSearchTopBar(scrollBehavior = scrollBehavior) }
    ) {innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
           // HomeScreen(paddingValues = innerPadding)
            FlightSearchNavHost(
                navController = rememberNavController(),
                paddingValues = innerPadding)

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        modifier = modifier,
        title = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.flight_icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.width(dimensionResource(R.dimen.padding_small)))
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.headlineSmall,
                )
            }

        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun FlightSearchTopAppBarPreview() {
    FlightSearchTopBar(TopAppBarDefaults.enterAlwaysScrollBehavior())
}