package com.gmail.remarkable.development.airrybnik.pm10

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.gmail.remarkable.development.airrybnik.RybnikPm10ViewModel
import com.gmail.remarkable.development.airrybnik.databinding.RybnikPm10FragmentBinding
import kotlin.math.roundToInt

/**
 * Fragment for PM10 from Rybnik
 */
class RybnikPm10Fragment : Fragment() {

    private val viewModel by viewModels<RybnikPm10ViewModel>()

    private lateinit var viewDataBinding: RybnikPm10FragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = RybnikPm10FragmentBinding.inflate(inflater, container, false)

        viewModel.response.observe(viewLifecycleOwner, Observer { newResp ->
            viewDataBinding.whatTextView.text = newResp.name
            val firstNonNull = newResp.values.firstOrNull { it.value != null }
            viewDataBinding.whenTextView.text =
                firstNonNull?.date ?: "brak danych"
            viewDataBinding.howManyTextView.text =
                firstNonNull?.value?.roundToInt().toString()
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                viewDataBinding.errorTextView.text = it
            }
        })

        return viewDataBinding.root
    }
}