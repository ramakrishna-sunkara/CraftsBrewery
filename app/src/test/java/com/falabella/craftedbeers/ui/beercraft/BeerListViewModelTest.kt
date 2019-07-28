package com.falabella.craftedbeers.ui.beercraft



import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.falabella.craftedbeers.data.Repository
import com.falabella.craftedbeers.data.model.BeerCraftModel
import com.falabella.craftedbeers.data.model.BeerCraftResponse
import com.falabella.craftedbeers.data.remote.ApiStatus
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.net.SocketException

@RunWith(JUnit4::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var userService: Repository

    lateinit var mainViewModel: BeerListViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.mainViewModel = BeerListViewModel(this.userService)
    }

    @Test
    fun fetchRepositories_positiveResponse() {
        Mockito.`when`(this.userService.loadBeerCraft()).thenAnswer {
            return@thenAnswer Single.just(ArgumentMatchers.anyList<BeerCraftModel>())
        }

        val observer = mock(Observer::class.java) as Observer<BeerCraftResponse>
        this.mainViewModel.beerCraftResponse().observeForever(observer)

        this.mainViewModel.loadBeerCraft()

        assertNotNull(this.mainViewModel.beerCraftResponse().value)
        assertEquals(ApiStatus.LOADING, this.mainViewModel.beerCraftResponse().value?.status)
    }
}