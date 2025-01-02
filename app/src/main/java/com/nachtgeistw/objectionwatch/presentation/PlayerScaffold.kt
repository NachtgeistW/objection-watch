package com.nachtgeistw.objectionwatch.presentation

import ObjectionLibraryPagerScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.pager.rememberPagerState
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.SwipeDismissableNavHostState
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavHostState


@Composable
public fun PlayerScaffold(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navHostState: SwipeDismissableNavHostState = rememberSwipeDismissableNavHostState(),
) {
    SwipeDismissableNavHost(
        startDestination = "Player",
        navController = navController,
        modifier = modifier,
        state = navHostState
    ) {
        composable("Player") {
            val pageState = rememberPagerState(initialPage = 0, pageCount = { 2 })

            ObjectionLibraryPagerScreen(
                pagerState = pageState,
                objectionScreen = {
                    ObjectionScreen(Modifier.fillMaxSize().background(Color.Transparent))
                },
                libraryScreen = {
                    LibraryScreen(modifier.fillMaxSize())
                },
                modifier = modifier
            )
        }
    }
}