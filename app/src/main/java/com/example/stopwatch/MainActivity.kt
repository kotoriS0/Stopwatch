package com.example.stopwatch

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.os.SystemClock.elapsedRealtime
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    // classwide static constant in kotlin
    companion object {
        // all static constants go here
        val TAG = "MainActivity"
        val DB = "debug"

        val STATE_TIME = "time"
        val STATE_BASE = "stopwatch.base"
        val STATE_IS_RUNNING = "isRunning"
    }

    lateinit var stopwatch: Chronometer
    lateinit var start: Button
    lateinit var reset: Button

    var isRunning = false
    var time = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.v(TAG, "onCreate")

        wireWidgets()

        // restore instance state if it exists
        if(savedInstanceState != null) {
            time = savedInstanceState.getLong(STATE_TIME)
            isRunning = savedInstanceState.getBoolean(STATE_IS_RUNNING)
            //stopwatch.base = SystemClock.elapsedRealtime() - time
            //time = SystemClock.elapsedRealtime()
            if(isRunning) {
                stopwatch.base = SystemClock.elapsedRealtime() - time
                stopwatch.start()
                start.setBackgroundColor(Color.rgb(224, 70, 50))
                start.text = "stop"
            }
            else {
                //stopwatch.base = stopwatch.base + SystemClock.elapsedRealtime() + time
                stopwatch.base = savedInstanceState.getLong(STATE_BASE) +SystemClock.elapsedRealtime() - time
            }
        }

        start.setOnClickListener {
            if(start.text.equals("stop")) {
                time = SystemClock.elapsedRealtime()
                stopwatch.stop()
                start.setBackgroundColor(Color.rgb(110, 0, 248))
                start.text = "start"
                isRunning = false
                Log.d(DB, "stop time: $time")
            }
            else {
                stopwatch.setBase(stopwatch.base + SystemClock.elapsedRealtime() - time)
                stopwatch.start()
                start.setBackgroundColor(Color.rgb(224, 70, 50))
                start.text = "stop"
                isRunning = true
                Log.d(DB, "start base:${stopwatch.base}")
            }
        }

        reset.setOnClickListener {
            stopwatch.setBase(SystemClock.elapsedRealtime());
            time = SystemClock.elapsedRealtime()
            stopwatch.stop();
            start.setBackgroundColor(Color.rgb(110, 0, 248))
            start.text = "start"
            isRunning = false
        }
    }

    // use to preserve state though orientation changes
    override fun onSaveInstanceState(outState: Bundle) {
        // calculate the display time if currently running
        if(isRunning) {
            time = SystemClock.elapsedRealtime() - stopwatch.base
        }
        else {
            Log.d(DB, "stopped time: $time")
            //time = SystemClock.elapsedRealtime() - stopwatch.base
            //time = stopwatch.base
            Log.d(DB, "stopped save time: $time")
        }
        // save key-value pairs to the bundle before superclass call
        outState.putLong(STATE_TIME, time)
        outState.putLong(STATE_BASE, stopwatch.base)
        outState.putBoolean(STATE_IS_RUNNING, isRunning)

        super.onSaveInstanceState(outState)
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