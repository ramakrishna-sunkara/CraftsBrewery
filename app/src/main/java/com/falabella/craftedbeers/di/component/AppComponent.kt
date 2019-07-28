package com.falabella.craftedbeers.di.component

import com.falabella.craftedbeers.di.module.AppModule
import com.falabella.craftedbeers.di.module.ServiceModule
import com.falabella.craftedbeers.ui.beercraft.BeerListActivity

import javax.inject.Singleton

import dagger.Component

/**
 * Created by Ramakrishna Sunkara on 22-05-2019
 */

@Singleton
@Component(modules = [
    ServiceModule::class,
    AppModule::class])
interface AppComponent {
    fun inject(target: BeerListActivity)
}