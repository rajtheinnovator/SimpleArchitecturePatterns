package com.enpassio.architecturepatterns.mvp

import android.os.Handler
import java.util.*

//model
class GetQuoteInteractorImpl : GetQuoteInteractor {

    private val arrayList = Arrays.asList(
            "Be yourself. Everyone else is already taken.",
            "A room without books is like a body without a soul.",
            "You only live once, but if you do it right, once is enough.",
            "Be the change that you wish to see in the world.",
            "If you tell the truth, you don\'t have to remember anything."
    )

    private val randomString: String
        get() {

            val random = Random()
            val index = random.nextInt(arrayList.size)

            return arrayList.get(index)
        }

    override fun getNextQuote(listener: GetQuoteInteractor.OnFinishedListener) {
        Handler().postDelayed({ listener.onFinished(randomString) }, 1200)
    }
}