package com.enpassio.architecturepatterns.mvvmExample

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.view.View

class MainViewModel: ViewModel() {
    val model = Model()
    var quote = MutableLiveData<String>()

    fun updateQuote(view: View) {
        quote.value = model.getNextQuote()
    }

    fun getCurrentQuote(): LiveData<String> {
        return quote
    }
}