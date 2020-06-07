/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.foobles.kotlinnum.notification.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.foobles.kotlinnum.BaseFragment
import com.foobles.kotlinnum.R
import com.foobles.kotlinnum.databinding.FragmentNotificationBinding
import com.foobles.kotlinnum.notification.vm.NotificationViewModel

class NotificationFragment : BaseFragment<FragmentNotificationBinding>() {

    private val notificationViewModel by viewModels<NotificationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createChannel(
            getString(R.string.practice_notification_channel_id),
            getString(R.string.practice_notification_channel_name)
        )
        loadUi()
    }

    private fun loadUi() {
        binding.onOffSwitch.isChecked = notificationViewModel.loadNotificationStatus()
        binding.onOffSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            notificationViewModel.toggleNotification(isChecked)
        }
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
                .apply {
                    setShowBadge(false)
                }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description =
                getString(R.string.practice_reminder_notification_channel_description)

            val notificationManager = requireActivity().getSystemService(
                NotificationManager::class.java
            )
            notificationManager?.createNotificationChannel(notificationChannel)

        }
    }
}

