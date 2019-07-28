package com.falabella.craftedbeers.ui.beercraft

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.falabella.craftedbeers.R
import com.falabella.craftedbeers.data.model.BeerCraftModel
import com.falabella.craftedbeers.data.model.BeerCraftResponse
import com.falabella.craftedbeers.ui.base.BaseActivity

import java.util.ArrayList
import java.util.LinkedHashSet

import javax.inject.Inject

import com.falabella.craftedbeers.data.remote.ApiStatus
import kotlinx.android.synthetic.main.activity_craft_beer.*
import kotlinx.android.synthetic.main.content_main.*

/**
 * Created by Ramakrishna Sunkara on 22-05-2019
 */
class BeerListActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {
    override val layoutId: Int
        get() = R.layout.activity_craft_beer

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    internal lateinit var viewModel: BeerListViewModel

    /*@BindView(R.id.rvArticles)
    internal var rvArticles: RecyclerView? = null

    @BindView(R.id.toolbarApp)
    internal var toolbarApp: Toolbar? = null

    @BindView(R.id.srlRefresh)
    internal var srlRefresh: SwipeRefreshLayout? = null*/

    internal lateinit var searchView: SearchView

    internal lateinit var beerListAdapter: BeerListAdapter
    internal lateinit var beerCraftModels: ArrayList<BeerCraftModel>
    internal var sortList = arrayOf("Alcohol Content Ascending", "Alcohol Content Descending")
    internal var filterList = arrayOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_craft_beer);
        component.inject(this)

        setSupportActionBar(toolbarApp)

        beerCraftModels = ArrayList()
        beerListAdapter = BeerListAdapter(beerCraftModels)
        rvArticles!!.adapter = beerListAdapter

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BeerListViewModel::class.java)

        viewModel.beerCraftResponse().observe(this, Observer { this.consumeArticlesResponse(it) })

        srlRefresh!!.setOnRefreshListener(this)

    }

    override fun onStart() {
        super.onStart()
        loadArticles()
    }

    private fun loadArticles() {
        if (!isNetworkConnected) {
            Toast.makeText(this@BeerListActivity, resources.getString(R.string.network_error), Toast.LENGTH_SHORT).show()
        } else {
            viewModel.loadBeerCraft()
        }
    }

    /*
     * method to handle renderArticlesResponse Response
     * */
    private fun consumeArticlesResponse(beerCraftResponse: BeerCraftResponse) {

        when (beerCraftResponse.status) {

            ApiStatus.LOADING -> srlRefresh!!.isRefreshing = true

            ApiStatus.SUCCESS -> {
                srlRefresh!!.isRefreshing = false
                renderArticlesResponse(beerCraftResponse.beerCraftModels)
            }

            ApiStatus.ERROR -> srlRefresh!!.isRefreshing = false

            else -> {
            }
        }
    }

    /*
     * method to handle renderArticlesResponse Success Response
     * */
    private fun renderArticlesResponse(beerCraftModels: ArrayList<BeerCraftModel>?) {
        if (null != beerCraftModels && beerCraftModels.size > 0) {
            beerListAdapter = BeerListAdapter(beerCraftModels)
            rvArticles!!.adapter = beerListAdapter
            val stringSet = LinkedHashSet<String>()
            for (model in beerCraftModels) {
                model.style?.let { stringSet.add(it) }
            }
            //filterList = stringSet?.size
            filterList = stringSet.toTypedArray()
        } else {
            Toast.makeText(applicationContext, R.string.empty_beer_crafts, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRefresh() {
        loadArticles()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search)
                .actionView as SearchView
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(componentName))
        searchView.maxWidth = Integer.MAX_VALUE

        // listening to search query text change
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // filter recycler view when query submitted
                beerListAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                // filter recycler view when text is changed
                beerListAdapter.filter.filter(query)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_search -> return true
            R.id.action_sort -> {
                showSortDialog()
                return true
            }
            R.id.action_filter -> {
                showFilterDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified) {
            searchView.isIconified = true
            return
        }
        super.onBackPressed()
    }

    fun showSortDialog() {
        val builder = AlertDialog.Builder(this@BeerListActivity)
        builder.setTitle(R.string.action_sort)
                .setItems(sortList) { dialog, which -> viewModel.sortBeerCrafts(sortList[which]) }
        builder.create()
        builder.show()
    }

    fun showFilterDialog() {
        if (filterList.size > 0) {
            val builder = AlertDialog.Builder(this@BeerListActivity)
            builder.setTitle(R.string.action_filter)
                    .setItems(filterList) { dialog, which -> viewModel.filterBeerCrafts(filterList[which]) }
            builder.create()
            builder.show()
        } else {
            Toast.makeText(this, getString(R.string.no_data_for_filter), Toast.LENGTH_SHORT).show()
        }
    }
}
