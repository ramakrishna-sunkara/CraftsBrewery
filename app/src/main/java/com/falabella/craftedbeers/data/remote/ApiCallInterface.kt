package com.falabella.craftedbeers.data.remote

import com.falabella.craftedbeers.data.model.BeerCraftModel
import com.falabella.craftedbeers.data.remote.ApiEndPoint.BEER_CRAFT

import java.util.ArrayList

import io.reactivex.Single
import retrofit2.http.GET
import com.falabella.craftedbeers.data.remote.ApiEndPoint as ApiEndPoint1

/**
 * Created by Ramakrishna Sunkara on 22-05-2019
 */


interface ApiCallInterface {

    @GET(BEER_CRAFT)
    fun loadBeerCraft(): Single<ArrayList<BeerCraftModel>>

}
