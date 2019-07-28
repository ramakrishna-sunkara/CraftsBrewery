package com.falabella.craftedbeers.di.module

import androidx.lifecycle.ViewModelProvider


import com.falabella.craftedbeers.data.remote.ApiCallInterface
import com.falabella.craftedbeers.data.Repository
import com.falabella.craftedbeers.ui.beercraft.ViewModelFactory

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

/**
 * Created by Ramakrishna Sunkara on 22-05-2019
 */

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideRepository(apiCallInterface: ApiCallInterface): Repository {
        return Repository(apiCallInterface)
    }

    @Provides
    @Singleton
    internal fun provideViewModelFactory(myRepository: Repository): ViewModelProvider.Factory {
        return ViewModelFactory(myRepository)
    }
}
