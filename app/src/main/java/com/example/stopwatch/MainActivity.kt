package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import android.widget.Chronometer

class MainActivity : AppCompatActivity() {

    // classwide static constant in kotlin
    companion object {
        // all static constants go here
        val TAG = "MainActivity"
    }

    lateinit var stopwatch: Chronometer
    lateinit var start: Button
    lateinit var reset: Button

    var time = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.v(TAG, "onCreate")

        wireWidgets()

        start.setOnClickListener {
            if(start.text.equals("stop")) {
                time = SystemClock.elapsedRealtime()
                stopwatch.stop()
                start.text = "start"
            }
            else {
                stopwatch.setBase(stopwatch.base + SystemClock.elapsedRealtime() - time)
                stopwatch.start()
                start.text = "stop"
            }
        }

        reset.setOnClickListener {
            stopwatch.setBase(SystemClock.elapsedRealtime());
            time = SystemClock.elapsedRealtime()
            stopwatch.stop();
            start.text = "start"
        }
    }

    private fun wireWidgets() {
        stopwatch = findViewById(R.id.chronometer_main_stopwatch)
        start = findViewById(R.id.button_main_start)
        reset = findViewById(R.id.button_main_reset)

        stopwatch.base = SystemClock.elapsedRealtime()
        start.text = "start"
        reset.text = "reset"

        time = stopwatch.base
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.w(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.e(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.wtf(TAG, "onDestroy")
    }


}