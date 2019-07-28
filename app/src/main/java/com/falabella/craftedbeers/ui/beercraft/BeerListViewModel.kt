package com.falabella.craftedbeers.ui.beercraft

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.falabella.craftedbeers.data.Repository
import com.falabella.craftedbeers.data.model.BeerCraftModel
import com.falabella.craftedbeers.data.model.BeerCraftResponse

import java.util.ArrayList
import java.util.Collections
import java.util.Objects

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Ramakrishna Sunkara on 22-05-2019
 */

class BeerListViewModel(private val repository: Repository) : ViewModel() {

    private val disposables = CompositeDisposable()
    private val beerCraftResponseMutableLiveData = MutableLiveData<BeerCraftResponse>()

    fun beerCraftResponse(): MutableLiveData<BeerCraftResponse> {
        return beerCraftResponseMutableLiveData
    }

    /*
     * method to call loadBeerCraft
     * */
    fun loadBeerCraft() {
        disposables.add(repository.loadBeerCraft()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { disposable -> beerCraftResponseMutableLiveData.setValue(BeerCraftResponse.loading()) }
                .subscribe({ beerCraftModels -> beerCraftResponseMutableLiveData.setValue(BeerCraftResponse.success(beerCraftModels)) },
                        { throwable -> beerCraftResponseMutableLiveData.setValue(BeerCraftResponse.error(throwable)) }))
    }

    /*
     * method to call sortBeerCraft
     * */
    fun sortBeerCrafts(sortType: String) {
        if (null == Objects.requireNonNull<BeerCraftResponse>(beerCraftResponseMutableLiveData.value).beerCraftModels) {
            return
        }
        disposables.add(processBeerCraftsSort(sortType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { disposable -> beerCraftResponseMutableLiveData.setValue(BeerCraftResponse.loading()) }
                .subscribe(
                        { articles -> beerCraftResponseMutableLiveData.setValue(BeerCraftResponse.success(articles)) },
                        { throwable -> beerCraftResponseMutableLiveData.setValue(BeerCraftResponse.error(throwable)) }
                ))
    }

    private fun processBeerCraftsSort(sortType: String): Observable<ArrayList<BeerCraftModel>> {
        return if (sortType == "Alcohol Content Ascending") {
            Observable.just(doBeerCraftsSortAscending())
        } else {
            Observable.just(doBeerCraftsSortDescending())
        }
    }

    private fun doBeerCraftsSortAscending(): ArrayList<BeerCraftModel>? {
        val mBeerCraftModels = Objects.requireNonNull<BeerCraftResponse>(beerCraftResponseMutableLiveData.value).beerCraftModels
        Collections.sort(mBeerCraftModels)
        return mBeerCraftModels
    }

    private fun doBeerCraftsSortDescending(): ArrayList<BeerCraftModel>? {
        val mBeerCraftModels = Objects.requireNonNull<BeerCraftResponse>(beerCraftResponseMutableLiveData.value).beerCraftModels
        val compareByDescending = { article1: BeerCraftModel, article2: BeerCraftModel -> java.lang.Float.compare(article2.ounces, article1.ounces) }
        Collections.sort(mBeerCraftModels, compareByDescending)
        return mBeerCraftModels
    }

    override fun onCleared() {
        disposables.clear()
    }

    fun filterBeerCrafts(filter: String) {
        if (null == Objects.requireNonNull<BeerCraftResponse>(beerCraftResponseMutableLiveData.value).beerCraftModels) {
            return
        }
        disposables.add(processBeerCraftsFilter(filter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { disposable -> beerCraftResponseMutableLiveData.setValue(BeerCraftResponse.loading()) }
                .subscribe(
                        { articles -> beerCraftResponseMutableLiveData.setValue(BeerCraftResponse.success(articles)) },
                        { throwable -> beerCraftResponseMutableLiveData.setValue(BeerCraftResponse.error(throwable)) }
                ))
    }

    private fun processBeerCraftsFilter(filter: String): Observable<ArrayList<BeerCraftModel>> {
        return Observable.just(doBeerCraftsFilter(filter))
    }

    private fun doBeerCraftsFilter(filter: String): ArrayList<BeerCraftModel>? {
        val mBeerCraftModels = Objects.requireNonNull<BeerCraftResponse>(beerCraftResponseMutableLiveData.value).beerCraftModels
        mBeerCraftModels?.retainAll{
            it.style.equals(filter)
        }
        return mBeerCraftModels
    }

}
