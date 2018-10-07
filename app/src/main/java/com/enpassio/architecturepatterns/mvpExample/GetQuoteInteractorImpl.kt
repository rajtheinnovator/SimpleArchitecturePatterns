package com.enpassio.architecturepatterns.mvpExample

/**
 * Created by Greta Grigutė on 2018-10-06.
 */

import android.os.Handler

import java.util.Arrays
import java.util.Random

class GetQuoteInteractorImpl : MainContract.GetQuoteInteractor {

    private val arrayList = Arrays.asList(
            "Be yourself. everyone else is already taken.",
            "A room without books is like a body without a soul.",
            "You only live once, but if you do it right, once is enough.",
            "Be the change that you wish to see in the world.",
            "If you tell the truth, you don't have to remember anything."
    )

    private val randomString: String
        get() {

            val random = Random()
            val index = random.nextInt(arrayList.size)

            return arrayList[index]
        }

    override fun getNextQuote(listener: MainContract.GetQuoteInteractor.OnFinishedListener) {
        Handler().postDelayed({ listener.onFinished(randomString) }, 1200)
    }
}
