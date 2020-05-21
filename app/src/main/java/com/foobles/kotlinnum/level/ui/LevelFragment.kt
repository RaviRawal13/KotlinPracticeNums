package com.foobles.kotlinnum.level.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.foobles.kotlinnum.BaseFragment
import com.foobles.kotlinnum.databinding.LevelFragmentBinding
import com.foobles.kotlinnum.databinding.StorageFragmentBinding
import com.foobles.kotlinnum.login.vm.LoginViewModel
import com.foobles.kotlinnum.storage.adapter.StorageAdapter
import com.foobles.kotlinnum.storage.vm.StorageViewModel
import com.foobles.kotlinnum.utils.ARG_LEVEL
import com.google.firebase.storage.StorageReference

class LevelFragment : BaseFragment() {
    private lateinit var binding: LevelFragmentBinding
    private val loginViewModel by activityViewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        showToolbar()
        binding = LevelFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val preferences = context?.getSharedPreferences("prefs", 0)
        val levelString = preferences?.getString(ARG_LEVEL, "")
        binding.buttonUser.text = "Welcome ${loginViewModel.email}\n $levelString"
        with(binding) {
            buttonBeginer.setOnClickListener {
                levelSelected(buttonBeginer.text)
            }
            buttonIntermediate.setOnClickListener {
                levelSelected(buttonIntermediate.text)
            }
            buttonAdvanced.setOnClickListener {
                levelSelected(buttonAdvanced.text)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun levelSelected(level: CharSequence) {
        val preferences = context?.getSharedPreferences("prefs", 0)
        Toast.makeText(
            context,
            "Preference selected $level",
            Toast.LENGTH_SHORT
        ).show()

        preferences?.edit()?.putString(ARG_LEVEL, level.toString())?.apply()
        binding.buttonUser.text = "Welcome ${loginViewModel.email}\n $level"
    }
}