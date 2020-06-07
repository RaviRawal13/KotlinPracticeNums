package com.foobles.kotlinnum.profile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.foobles.kotlinnum.BaseFragment
import com.foobles.kotlinnum.databinding.ProfileFragmentBinding
import com.foobles.kotlinnum.login.vm.LoginViewModel
import com.foobles.kotlinnum.utils.*

class ProfileFragment : BaseFragment<ProfileFragmentBinding>() {

    private val loginViewModel by activityViewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        showToolbar()
        binding = ProfileFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val preferences = context?.getSharedPreferences("prefs", 0)
            val levelString = preferences?.getString(ARG_LEVEL, "")
            textviewTitle.text = "Welcome ${loginViewModel.email}, $levelString"

            val currentSum = preferences?.getInt(CORRECT_SUM, 0)
            val totalSum = preferences?.getInt(TOTAL_SUM, 0)
            tvCA.text = "Correct $currentSum"
            tvTA.text = "Total $totalSum"
            val currentSub = preferences?.getInt(CORRECT_SUB, 0)
            val totalSub = preferences?.getInt(TOTAL_SUB, 0)
            tvCS.text = "Correct $currentSub"
            tvTS.text = "Total $totalSub"
            val currentMul = preferences?.getInt(CORRECT_MUL, 0)
            val totalSMul = preferences?.getInt(TOTAL_MUL, 0)
            tvCM.text = "Correct $currentMul"
            tvTM.text = "Total $totalSMul"
            val currentDiv = preferences?.getInt(CORRECT_DIV, 0)
            val totalDiv = preferences?.getInt(TOTAL_DIV, 0)
            tvCD.text = "Correct $currentDiv"
            tvTD.text = "Total $totalDiv"
            val currentMul11 = preferences?.getInt(CORRECT_MUL_11, 0)
            val totalMul11 = preferences?.getInt(TOTAL_MUL_11, 0)
            tvCM11.text = "Correct $currentMul11"
            tvTM11.text = "Total $totalMul11"
            val currentMul25 = preferences?.getInt(CORRECT_MUL_25, 0)
            val totalMul25 = preferences?.getInt(TOTAL_MUL_25, 0)
            tvCM25.text = "Correct $currentMul25"
            tvTM25.text = "Total $totalMul25"
            val currentMul75 = preferences?.getInt(CORRECT_MUL_75, 0)
            val totalMul75 = preferences?.getInt(TOTAL_MUL_75, 0)
            tvCM75.text = "Correct $currentMul75"
            tvTM75.text = "Total $totalMul75"

        }
    }

}