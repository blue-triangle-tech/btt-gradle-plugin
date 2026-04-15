package com.bluetriangle.navigation3app

import android.app.Application
import android.util.Log
import com.bluetriangle.analytics.BlueTriangleConfiguration
import com.bluetriangle.analytics.Tracker

class Nav3SampleApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        val configuration = BlueTriangleConfiguration()
        configuration.siteId = "sdkdevtest500z"
        configuration.isDebug = true
        configuration.debugLevel = Log.VERBOSE
        Tracker.init(this, configuration)
    }
}