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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
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

@Composable
fun Objection(context: Context) {
    val navController = rememberSwipeDismissableNavController()

    PlayerScaffold(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        context = context
    )
}