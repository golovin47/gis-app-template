package com.gis.jetpackplayground.application

import android.app.Application
import com.gis.jetpackplayground.di.koinModule
import org.koin.android.ext.android.startKoin

class JetPackApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(koinModule))
    }
}