package com.falabella.craftedbeers.ui.beercraft

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.falabella.craftedbeers.R
import com.falabella.craftedbeers.data.model.BeerCraftModel

import java.util.ArrayList

class BeerListAdapter(private val beerCraftModels: ArrayList<BeerCraftModel>) : RecyclerView.Adapter<BeerListAdapter.ArticlesViewHolder>(), Filterable {
    private var beerCraftModelsFiltered: ArrayList<BeerCraftModel>? = null
    private var context: Context? = null

    init {
        beerCraftModelsFiltered = beerCraftModels
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context = parent.context
        val listItem = layoutInflater.inflate(R.layout.item_beers_list, parent, false)
        return ArticlesViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        val beerCraftModel = beerCraftModelsFiltered!![position]
        holder.txtBeerName!!.text = beerCraftModel.name
        holder.txtBeerStyle!!.text = beerCraftModel.style
        holder.txtOunces!!.text = context!!.getString(R.string.alcohol_content, beerCraftModel.ounces)
    }

    override fun getItemCount(): Int {
        return beerCraftModelsFiltered!!.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    beerCraftModelsFiltered = beerCraftModels
                } else {
                    val filteredList = ArrayList<BeerCraftModel>()
                    for (beerCraftModel in beerCraftModels) {
                        if (beerCraftModel.name!!.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(beerCraftModel)
                        }
                    }

                    beerCraftModelsFiltered = filteredList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = beerCraftModelsFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                beerCraftModelsFiltered = filterResults.values as ArrayList<BeerCraftModel>
                notifyDataSetChanged()
            }
        }
    }

    inner class ArticlesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtBeerName: TextView? = itemView.findViewById(R.id.txtBeerName)
        var txtBeerStyle: TextView? = itemView.findViewById(R.id.txtBeerStyle)
        var txtOunces: TextView? = itemView.findViewById(R.id.txtOunces)

    }
}