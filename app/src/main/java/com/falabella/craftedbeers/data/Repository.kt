package com.falabella.craftedbeers.data

import com.falabella.craftedbeers.data.model.BeerCraftModel
import com.falabella.craftedbeers.data.remote.ApiCallInterface

import java.util.ArrayList

import io.reactivex.Single

/**
 * Created by Ramakrishna Sunkara on 22-05-2019
 */

class Repository(private val apiCallInterface: ApiCallInterface) {

    /*
     * method to call loadBeerCraft api
     * */
    fun loadBeerCraft(): Single<ArrayList<BeerCraftModel>> {
        return apiCallInterface.loadBeerCraft()
    }
}
