package com.example.flightsearch.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flightsearch.ui.AirportViewModel
import com.example.flightsearch.ui.AppViewModelProvider
import com.example.flightsearch.ui.HomeDestination
import com.example.flightsearch.ui.HomeScreen
import com.example.flightsearch.ui.SearchDestination
import com.example.flightsearch.ui.SearchScreen
import kotlinx.coroutines.delay

@Composable
fun FlightSearchNavHost(
    navController: NavHostController,
    paddingValues: PaddingValues,
    airportViewModel: AirportViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {

    var isNavigating by remember { mutableStateOf(false) }
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
      composable(route = HomeDestination.route) {
          HomeScreen(
              onSearchClick = {
                  if(!isNavigating) {
                      isNavigating = true
                      navController.navigate(SearchDestination.route)
                  }
                              },
              paddingValues = paddingValues,
              airportViewModel = airportViewModel
          )
      }

      composable(
          route = SearchDestination.route,
          enterTransition = {
              slideIntoContainer(
                  AnimatedContentTransitionScope.SlideDirection.Up,
                  animationSpec = tween(700)
              )
          },
          exitTransition = {
              slideOutOfContainer(
                  AnimatedContentTransitionScope.SlideDirection.Down,
                  animationSpec = tween(700)
              )
          },
          popExitTransition = {
              slideOutOfContainer(
                  AnimatedContentTransitionScope.SlideDirection.Down,
                  animationSpec = tween(700)
              )
          }
      ) {
          LaunchedEffect(key1 = isNavigating) {
              delay(720)
              isNavigating = false
          }
          SearchScreen(
              navigateBack = { navController.navigateUp() },
              paddingValues = paddingValues,
              airportViewModel = airportViewModel
          )

      }
        }
}

