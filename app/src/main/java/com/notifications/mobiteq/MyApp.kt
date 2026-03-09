package com.notifications.mobiteq

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        // Initialize Firebase FIRST before Hilt builds its graph
        FirebaseApp.initializeApp(this)
        super.onCreate()
    }
}
