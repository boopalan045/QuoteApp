package com.asustug.quoteapp

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import java.io.InputStream

class MainViewModel(val context: Context): ViewModel() {
    private var quoteList: Array<Quote> = emptyArray()

    private val TAG = MainViewModel::class.java.name

    private var index = 0

    private var inputStream : InputStream? = null

    init {
        quoteList = loadQuoteFromAssets()
    }

    private fun loadQuoteFromAssets(): Array<Quote> {
        try {
           inputStream  = context.assets.open("quotes.json")
        }catch (e: Exception) {
            Log.d(TAG, "loadQuoteFromAssets: ")
        }
        val size: Int = inputStream!!.available()
        val buffer = ByteArray(size)
        inputStream!!.read(buffer)
        inputStream!!.close()
        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        return gson.fromJson(json, Array<Quote>::class.java)
    }

    fun getQuote() = quoteList[index]

    fun nextQuote(): Quote {
        Log.d(TAG, "nextQuote: " + (++index % quoteList.size))
        return quoteList[++index % quoteList.size]
    }

    fun previousQuote(): Quote {
        Log.d(TAG, "previousQuote: " + (--index % quoteList.size))
        return quoteList[(--index + quoteList.size) % quoteList.size]
    }
}
