package com.enpassio.architecturepatterns.mvpfinishedwithoutrxanddagger.ui.list

import android.app.ListFragment
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import com.enpassio.architecturepatterns.R
import com.enpassio.architecturepatterns.mvpfinishedwithoutrxanddagger.ui.base.BaseActivity

/**
 * Created by Greta Grigutė on 2018-10-17.
 */
class ListActivity : BaseActivity() {
    var listFragment = ListFragment
    override  fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme) // Branded launch
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mvp_finished_without_rx_and_dagger_activity)
        if (savedInstanceState == null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.list_container, listFragment.newInstance())
                    .commit()
        }
        showCopyRightSnackbar()
    }

    protected fun showCopyRightSnackbar() {

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        if (sharedPreferences.getBoolean(KEY_MARVEL_COPYRIGHT, false)) {
            Log.i("BaseActivity", "User already knows")
            return
        }

        val copyrightSnackbar = Snackbar.make(findViewById(R.id.list_container),
                getString(R.string.marvel_copyright_notice), Snackbar.LENGTH_INDEFINITE)
        copyrightSnackbar.setAction(R.string.action_acknowledge, View.OnClickListener {
            sharedPreferences.edit().putBoolean(KEY_MARVEL_COPYRIGHT, true).apply()
            copyrightSnackbar.dismiss()
        })
        copyrightSnackbar.show()
    }

    companion object {

        protected val KEY_MARVEL_COPYRIGHT = "keyMarvelCopyRight"
    }

}
