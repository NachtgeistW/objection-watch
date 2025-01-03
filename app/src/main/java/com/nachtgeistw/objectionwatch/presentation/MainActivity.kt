/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.nachtgeistw.objectionwatch.presentation

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.material.PageIndicatorState
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import androidx.wear.compose.navigation.rememberSwipeDismissableNavHostState
import com.nachtgeistw.objectionwatch.R
import kotlin.math.abs

class MainActivity : ComponentActivity(), SensorEventListener {
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        mediaPlayer = MediaPlayer.create(this, R.raw.se00e);

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);


        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            Objection(this)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        handleAccelerometerData(event.values)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private var lastX = 0f
    private var lastY = 0f
    private var lastZ = 0f
    private var gestureStartTime: Long = 0

    private fun handleAccelerometerData(value: FloatArray) {
        val x = value[0]
        val y = value[1]
        val z = value[2]

        val THERSHOLD_X = 10
        val THERSHOLD_Y = 20
        val THERSHOLD_Z = 8
        val THERSHOLD_TIME = 1000

        //var tempY = abs(y - lastY)
        //Log.i("igiari", "acceletor $lastY $y $tempY $lastX $x")
        if (abs(y - lastY) > THERSHOLD_Y &&
            abs(x - lastX) > THERSHOLD_X
        ) {
            val curTime = System.currentTimeMillis()
            var tempTime = curTime - gestureStartTime
            //Log.i("igiari", "$tempY $x $tempTime")
            if (curTime - gestureStartTime < THERSHOLD_TIME) {
                onGestureDetected()
            }
            gestureStartTime = curTime
        }
        lastX = x
        lastY = y
        lastZ = z
    }

    private fun onGestureDetected() {
        playMedia(this, R.raw.se00e)
        //Log.i("igiari","igiari!")
    }
}

//@Composable
//private fun Igiari(context: Context) {
//    val maxPages = 3
//    var selectedPage by remember { mutableIntStateOf(1) }
//    var finalValue by remember { mutableIntStateOf(1) }
//    val pagerState = rememberPagerState(initialPage = 1, pageCount = { maxPages })
//
//    val animatedSelectedPage by
//    animateFloatAsState(
//        targetValue = selectedPage.toFloat(), label = "",
//    ) {
//        finalValue = it.toInt()
//    }
//    val pageIndicatorState: PageIndicatorState = remember {
//        object : PageIndicatorState {
//            override val pageOffset: Float
//                get() = animatedSelectedPage - finalValue
//
//            override val selectedPage: Int
//                get() = finalValue
//
//            override val pageCount: Int
//                get() = maxPages
//        }
//    }
//
//    Scaffold(
//        modifier = Modifier
//            .fillMaxSize()
//    )
//    {
//        Box(
//            modifier = Modifier
//                .fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            HorizontalPager(
//                modifier = Modifier.fillMaxSize(),
//                state = pagerState
//            ) { page ->
//                IgiariButton(
//                    modifier = Modifier.fillMaxSize(),
//                    onClick = { playMedia(context, R.raw.se00e) }
//                )
////                Text(
////                    modifier = Modifier.align(alignment = Alignment.Center),
////                    text = "Page: $page",
////                )
//                selectedPage = page
//            }
//
//            HorizontalPageIndicator(
//                pageIndicatorState = pageIndicatorState,
//                modifier = Modifier.align(Alignment.TopCenter)
//            )
//        }
//    }
//}

@Composable
fun Objection(context: Context) {
    val navController = rememberSwipeDismissableNavController()
    val navHostState = rememberSwipeDismissableNavHostState()

    // Track current page for page indicator

    var currentPage by remember { mutableStateOf(0) }
    val totalPages = 2 // Number of main pages (Library and Player)
    var finalValue by remember { mutableIntStateOf(0) }
    val animatedSelectedPage by
    animateFloatAsState(
        targetValue = currentPage.toFloat(), label = "",
    ) {
        finalValue = it.toInt()
    }
    val pageIndicatorState: PageIndicatorState = remember {
        object : PageIndicatorState {
            override val pageOffset: Float
                get() = animatedSelectedPage - finalValue

            override val selectedPage: Int
                get() = finalValue

            override val pageCount: Int
                get() = totalPages
        }
    }

    PlayerScaffold(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        context = context
    )
}