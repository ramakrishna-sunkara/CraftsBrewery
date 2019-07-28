package com.falabella.craftedbeers.ui.beercraft

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.falabella.craftedbeers.data.Repository

import javax.inject.Inject

/**
 * Created by Ramakrishna Sunkara on 22-05-2019
 */

class ViewModelFactory @Inject
constructor(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BeerListViewModel::class.java)) {
            return BeerListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
