package com.nachtgeistw.objectionwatch.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.wear.compose.foundation.HierarchicalFocusCoordinator
import androidx.wear.compose.foundation.pager.HorizontalPager
import androidx.wear.compose.foundation.pager.PagerState
import androidx.wear.compose.material.PageIndicatorState
import androidx.wear.compose.material.Scaffold

/**
 * A Wear Material Compliant Pager screen.
 *
 * Combines the Compose Foundation Pager, with the Wear Compose HorizontalPageIndicator.
 *
 * The current page gets the Hierarchical Focus.
 */
@Composable
public fun PagerScreen(
    state: PagerState,
    modifier: Modifier = Modifier,
    beyondViewportPageCount: Int = 0,
    userScrollEnabled: Boolean = true,
    reverseLayout: Boolean = false,
    key: ((index: Int) -> Any)? = null,
    content: @Composable (Int) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        content = {
            Box {
                HorizontalPager(
                    modifier = modifier.fillMaxSize(),
                    state = state
                ) { page ->
                    //Focus on current page
                    HierarchicalFocusCoordinator(requiresFocus = {page == state.currentPage}) {
                        content(page)
                    }
                }
            }
        }
    )
}

@Composable
internal fun ClippedBox(pagerState: PagerState, content: @Composable () -> Unit) {
    val shape = rememberClipWhenScrolling(pagerState)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .optionalClip(shape),
    ) {
        content()
    }
}

@Composable
private fun rememberClipWhenScrolling(state: PagerState): State<RoundedCornerShape?> {
    val shape = if (LocalConfiguration.current.isScreenRound) CircleShape else null
    return remember(state) {
        derivedStateOf {
            if (shape != null && state.currentPageOffsetFraction != 0f) {
                shape
            } else {
                null
            }
        }
    }
}

private fun Modifier.optionalClip(shapeState: State<RoundedCornerShape?>): Modifier {
    val shape = shapeState.value

    return if (shape != null) {
        clip(shape)
    } else {
        this
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
