package com.falabella.craftedbeers

import android.app.Application
import android.content.Context

import com.falabella.craftedbeers.di.component.AppComponent
import com.falabella.craftedbeers.di.component.DaggerAppComponent
import com.falabella.craftedbeers.di.module.AppModule
import com.falabella.craftedbeers.di.module.ServiceModule


/**
 * Created by Ramakrishna Sunkara on 22-05-2019
 */

class MyApplication : Application() {

    lateinit var appComponent: AppComponent
    internal lateinit var context: Context

    override fun onCreate() {
        super.onCreate()
        context = this
        appComponent = DaggerAppComponent.builder().serviceModule(ServiceModule(context)).appModule(AppModule()).build()
    }
}
