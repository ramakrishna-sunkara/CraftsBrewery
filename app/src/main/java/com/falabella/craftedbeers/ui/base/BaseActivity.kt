package com.falabella.craftedbeers.ui.base

import android.app.ProgressDialog
import android.os.Bundle

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

import com.falabella.craftedbeers.MyApplication
import com.falabella.craftedbeers.di.component.AppComponent
import com.falabella.craftedbeers.utils.NetworkUtils

import butterknife.ButterKnife

/**
 * Created by Ramakriishna Sunkara on 23/05/19
 */

abstract class BaseActivity : AppCompatActivity() {


    private val mProgressDialog: ProgressDialog? = null

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    val isNetworkConnected: Boolean
        get() = NetworkUtils.isNetworkConnected(applicationContext)

    val component: AppComponent
        get() = (application as MyApplication).appComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(layoutId)
        ButterKnife.bind(this)
        super.onCreate(savedInstanceState)
    }

}

