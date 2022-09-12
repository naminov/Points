package com.example.points.app

import android.app.Application
import com.example.points.di.AppComponent
import com.example.points.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .build()
    }
}