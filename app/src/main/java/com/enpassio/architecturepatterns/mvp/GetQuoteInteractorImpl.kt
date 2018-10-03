package com.enpassio.architecturepatterns.mvp

import android.content.res.Resources
import android.os.Handler
import com.enpassio.architecturepatterns.R
import java.util.*


class GetQuoteInteractorImpl : GetQuoteInteractor {

    private val arrayList = Arrays.asList(
            Resources.getSystem().getString(R.string.quote_be_yourself),
            Resources.getSystem().getString(R.string.quote_room_without_book),
            Resources.getSystem().getString(R.string.quote_you_only_live_once),
            Resources.getSystem().getString(R.string.quote_be_the_change),
            Resources.getSystem().getString(R.string.quote_if_you_tell_the_truth)
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