package com.falabella.craftedbeers.data.model

import com.falabella.craftedbeers.data.remote.ApiStatus

import java.util.ArrayList

import io.reactivex.annotations.NonNull
import io.reactivex.annotations.Nullable

import com.falabella.craftedbeers.data.remote.ApiStatus.ERROR
import com.falabella.craftedbeers.data.remote.ApiStatus.LOADING
import com.falabella.craftedbeers.data.remote.ApiStatus.SUCCESS

/**
 * Created by Ramakrishna Sunkara on 22-05-2019
 */

class BeerCraftResponse private constructor(val status: ApiStatus, @param:Nullable @field:Nullable
val beerCraftModels: ArrayList<BeerCraftModel>?, @param:Nullable @field:Nullable
                                            val error: Throwable?) {
    companion object {

        fun loading(): BeerCraftResponse {
            return BeerCraftResponse(LOADING, null, null)
        }

        fun success(@NonNull beerCraftModels: ArrayList<BeerCraftModel>): BeerCraftResponse {
            return BeerCraftResponse(SUCCESS, beerCraftModels, null)
        }

        fun error(@NonNull error: Throwable): BeerCraftResponse {
            return BeerCraftResponse(ERROR, null, error)
        }
    }

}
