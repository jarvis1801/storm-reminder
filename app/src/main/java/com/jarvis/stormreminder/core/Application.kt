package com.jarvis.stormreminder.core

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

class Application : MultiDexApplication() {
    companion object {
        private var instance: Application? = null

        fun getInstance(): Application {
            if (instance == null) {
                instance = Application()
            }

            return instance!!
        }
    }


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}