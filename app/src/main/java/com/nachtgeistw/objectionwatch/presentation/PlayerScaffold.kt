package com.nachtgeistw.objectionwatch.presentation

import ObjectionLibraryPagerScreen
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.pager.PagerState
import androidx.wear.compose.foundation.pager.rememberPagerState
import androidx.wear.compose.material.HorizontalPageIndicator
import androidx.wear.compose.material.PageIndicatorState
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.SwipeDismissableNavHostState
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavHostState
import com.nachtgeistw.objectionwatch.R


@Composable
public fun PlayerScaffold(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navHostState: SwipeDismissableNavHostState = rememberSwipeDismissableNavHostState(),
    context: Context
) {
    SwipeDismissableNavHost(
        startDestination = "Player",
        navController = navController,
        modifier = modifier,
        state = navHostState
    ) {
        composable("Player") {
            val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })
            val pageIndicatorState = remember(pagerState) { PageScreenIndicatorState(pagerState) }

            ObjectionLibraryPagerScreen(
                pagerState = pagerState,
                objectionScreen = {
                    ObjectionScreen(
                        Modifier
                            .fillMaxSize()
                            .background(Color.Transparent),
                        onClick = { playMedia(context, R.raw.se00e) }
                    )
                },
                libraryScreen = {
                    LibraryScreen(modifier.fillMaxSize())
                },
                modifier = modifier
            )

            HorizontalPageIndicator(
                modifier = Modifier.padding(6.dp),
                pageIndicatorState = pageIndicatorState
            )
        }
    }
}

/**
 * Bridge between Foundation PagerState and the Wear Compose PageIndicatorState.
 */
public class PageScreenIndicatorState(
    private val state: PagerState,
) : PageIndicatorState {
    override val pageCount: Int
        get() = state.pageCount

    override val pageOffset: Float
        get() = state.currentPageOffsetFraction.takeIf { it.isFinite() } ?: 0f

    override val selectedPage: Int
        get() = state.currentPage
}
