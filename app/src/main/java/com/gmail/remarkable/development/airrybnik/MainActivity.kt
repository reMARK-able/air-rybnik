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
            val tv = findViewById<TextView>(R.id.response_textView)
            tv.text = newResp
        })

    }
}