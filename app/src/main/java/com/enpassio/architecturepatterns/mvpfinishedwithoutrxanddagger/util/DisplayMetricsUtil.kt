package com.enpassio.architecturepatterns.mvpfinishedwithoutrxanddagger.util

import android.content.res.Resources

/**
 * Created by Greta Grigutė on 2018-10-18.
 */
object DisplayMetricsUtil {

    /**
     * Return true if the width in DP of the device is equal or greater than the given value
     */
    fun isScreenW(widthDp: Int): Boolean {
        val displayMetrics = Resources.getSystem().displayMetrics
        val screenWidth = displayMetrics.widthPixels / displayMetrics.density
        return screenWidth >= widthDp
    }

}