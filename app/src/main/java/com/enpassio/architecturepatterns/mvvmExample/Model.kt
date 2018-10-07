package com.enpassio.architecturepatterns.mvvmExample

import java.util.*

class Model {

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

    fun getNextQuote(): String {
        return randomString
    }

}