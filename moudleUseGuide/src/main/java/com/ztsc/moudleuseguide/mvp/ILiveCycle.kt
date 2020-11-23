package com.ztsc.house.mvp

import android.os.Bundle

interface ILiveCycle {

    fun onCreate(saveInstanceState: Bundle?)

    fun onSaveInstanceState(outState: Bundle)

    fun onViewStateRestored(saveInstanceState: Bundle?)


    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onDestroy()


}