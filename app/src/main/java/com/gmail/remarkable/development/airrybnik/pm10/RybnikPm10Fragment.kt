package com.gmail.remarkable.development.airrybnik.pm10

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.gmail.remarkable.development.airrybnik.MainViewModel
import com.gmail.remarkable.development.airrybnik.R

/**
 * Fragment for PM10 from Rybnik
 */
class RybnikPm10Fragment : Fragment() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.rybnik_pm10_fragment, container, false)

        viewModel.response.observe(viewLifecycleOwner, Observer { newResp ->
            root.findViewById<TextView>(R.id.type_textView).text = newResp.name
            val firstNonNull = newResp.values.firstOrNull { it.value != null }
            root.findViewById<TextView>(R.id.date_textView).text =
                firstNonNull?.date ?: "brak danych"
            root.findViewById<TextView>(R.id.value_textView).text = firstNonNull?.value.toString()
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                root.findViewById<TextView>(R.id.error_textView).text = it
            }
        })

        return root
    }
}