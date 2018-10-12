package com.enpassio.architecturepatterns.mvvmjournaldevexamplebyabhishek.views

import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.enpassio.architecturepatterns.R
import com.enpassio.architecturepatterns.databinding.MvvmPatternJournaldevExampleByAbhishekActivityBinding
import com.enpassio.architecturepatterns.mvvmjournaldevexamplebyabhishek.viewmodel.LoginViewModel


class MvvmJournaldevExampleByAbhishekActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding =
                DataBindingUtil.setContentView<MvvmPatternJournaldevExampleByAbhishekActivityBinding>(this,
                        R.layout.mvvm_pattern_journaldev_example_by_abhishek_activity)
        activityMainBinding.setViewModel(LoginViewModel())
        activityMainBinding.executePendingBindings()

    }

    @BindingAdapter("bind:toastMessage")
    fun runMe(view: View, message: String?) {
        if (message != null)
            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show()
    }
}





