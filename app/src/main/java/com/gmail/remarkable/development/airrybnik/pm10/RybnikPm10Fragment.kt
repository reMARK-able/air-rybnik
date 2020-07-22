package com.gmail.remarkable.development.airrybnik.pm10

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gmail.remarkable.development.airrybnik.RybnikPm10ViewModel
import com.gmail.remarkable.development.airrybnik.databinding.RybnikPm10FragmentBinding

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

        viewDataBinding.viewModel = viewModel

        viewDataBinding.lifecycleOwner = viewLifecycleOwner

        return viewDataBinding.root
    }
}