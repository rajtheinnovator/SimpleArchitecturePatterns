package com.enpassio.architecturepatterns.mvvmExample

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil.setContentView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.enpassio.architecturepatterns.R
import com.enpassio.architecturepatterns.databinding.MvvmPatternActivityBinding

class MvvmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = setContentView<MvvmPatternActivityBinding>(this, R.layout.mvvm_pattern_activity)
        binding.viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.setLifecycleOwner(this)
    }
}
