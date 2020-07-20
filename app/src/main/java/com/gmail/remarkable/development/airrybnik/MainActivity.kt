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
            findViewById<TextView>(R.id.date_textView).text = newResp.values[0].date
            findViewById<TextView>(R.id.value_textView).text = newResp.values[0].value.toString()
        })

        viewModel.errorMessage.observe(this, Observer { error ->
            error?.let {
                findViewById<TextView>(R.id.error_textView).text = it
            }
        })
    }
}