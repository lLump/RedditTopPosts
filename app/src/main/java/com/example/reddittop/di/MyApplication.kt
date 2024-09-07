package com.example.reddittop.di

import android.app.Application

class MyApplication: Application() {
    lateinit var apiContainer: ApiContainer

    override fun onCreate() {
        super.onCreate()
        apiContainer = ApiContainer()
    }
}