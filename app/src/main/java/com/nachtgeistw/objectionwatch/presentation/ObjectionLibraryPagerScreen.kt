
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.foundation.pager.PagerState
import com.nachtgeistw.objectionwatch.presentation.PagerScreen

@Composable
public fun ObjectionLibraryPagerScreen(
    pagerState: PagerState,
    objectionScreen: @Composable () -> Unit,
    libraryScreen: @Composable () -> Unit,
    modifier: Modifier
) {
    PagerScreen(
        modifier = modifier.background(Color.Transparent),
        state = pagerState,
    ) { page ->
        when (page) {
            0 -> {
                objectionScreen()
            }
            1 -> {
                libraryScreen()
            }
        }
    }
}