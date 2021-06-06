package com.aditya.task_list

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//Application Class to Allow Dependency Injection
@HiltAndroidApp
class App : Application()