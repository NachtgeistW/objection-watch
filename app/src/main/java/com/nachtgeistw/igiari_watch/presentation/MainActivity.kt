/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.nachtgeistw.igiari_watch.presentation

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import com.nachtgeistw.igiari_watch.R
import kotlin.math.abs

class MainActivity : ComponentActivity(), SensorEventListener {
    private lateinit var mediaPlayer:MediaPlayer
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
            Igiari(this)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        handleAccelerometerData(event.values)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {    }

    private var lastX = 0f
    private var lastY = 0f
    private var lastZ = 0f
    private var gestureStartTime: Long = 0

    private fun handleAccelerometerData(value: FloatArray)
    {
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
            abs(x - lastX) > THERSHOLD_X)
        {
            val curTime = System.currentTimeMillis()
            var tempTime = curTime - gestureStartTime
            //Log.i("igiari", "$tempY $x $tempTime")
            if (curTime - gestureStartTime < THERSHOLD_TIME)
            {
                onGestureDetected()
            }
            gestureStartTime = curTime
        }
        lastX = x
        lastY = y
        lastZ = z
    }

    private fun onGestureDetected()
    {
        playMedia(this, R.raw.se00e)
        //Log.i("igiari","igiari!")
    }
}

fun playMedia(context: Context, audioId: Int)
{
    val mediaPlayer = MediaPlayer.create(context, audioId);
    mediaPlayer.start()
}

@Composable
private fun Igiari(context: Context) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = Modifier.fillMaxSize(),
            onClick = { playMedia(context, R.raw.se00e) }
        ) {
//            Icon(
//                imageVector = Icons.Rounded.Phone,
//                contentDescription = "triggers phone action",
//                modifier = iconModifier
//            )
            Image(
                modifier = Modifier.fillMaxSize(),
                bitmap = ImageBitmap.imageResource(id = R.drawable.etc00a),
                contentDescription = "play igiari sound"
            )
        }
    }
}