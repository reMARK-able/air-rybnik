package com.gmail.remarkable.development.airrybnik

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.response.observe(this, Observer { newResp ->
            findViewById<TextView>(R.id.type_textView).text = newResp.name
            val firstNonNull = newResp.values.firstOrNull { it.value != null }
            findViewById<TextView>(R.id.date_textView).text = firstNonNull?.date ?: "brak danych"
            findViewById<TextView>(R.id.value_textView).text = firstNonNull?.value.toString()
        })

        viewModel.errorMessage.observe(this, Observer { error ->
            error?.let {
                findViewById<TextView>(R.id.error_textView).text = it
            }
        })
    }
}