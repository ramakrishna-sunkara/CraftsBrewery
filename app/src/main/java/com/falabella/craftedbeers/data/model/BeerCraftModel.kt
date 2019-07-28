package com.falabella.craftedbeers.data.model

import java.util.Comparator

class BeerCraftModel : Comparable<BeerCraftModel> {

    var abv: String? = null
    var ibu: String? = null
    var id: Long = 0
    var name: String? = null
    var style: String? = null
    var ounces: Float = 0.toFloat()

    override fun compareTo(beerCraftModel: BeerCraftModel): Int {
        return java.lang.Float.compare(ounces, beerCraftModel.ounces)

    }
}
