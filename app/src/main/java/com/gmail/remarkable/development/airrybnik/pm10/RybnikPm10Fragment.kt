package com.gmail.remarkable.development.airrybnik.pm10

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gmail.remarkable.development.airrybnik.R
import com.gmail.remarkable.development.airrybnik.databinding.RybnikPm10FragmentBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment for PM10 from Rybnik
 */
@AndroidEntryPoint
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

        createNotificationChannel(
            getString(R.string.dataUpdate_notification_channel_id),
            getString(R.string.dataUpdate_notification_channel_name)
        )

        return viewDataBinding.root
    }

    private fun createNotificationChannel(channelId: String, channelName: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.apply {
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                description = "Aktualizacja danych"
            }
            val notificationManager =
                requireActivity().getSystemService(NotificationManager::class.java) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}