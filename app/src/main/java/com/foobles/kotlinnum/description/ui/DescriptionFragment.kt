package com.foobles.kotlinnum.description.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.foobles.kotlinnum.BaseFragment
import com.foobles.kotlinnum.R
import com.foobles.kotlinnum.databinding.DescriptionFragmentBinding
import com.foobles.kotlinnum.entity.StudyEntity
import com.foobles.kotlinnum.nav.NavigationActivity
import com.foobles.kotlinnum.utils.ARG_STUDY_ENTITY

class DescriptionFragment : BaseFragment() {
    private lateinit var binding: DescriptionFragmentBinding
    var studyEntity: StudyEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        studyEntity = arguments?.getParcelable(ARG_STUDY_ENTITY)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DescriptionFragmentBinding.inflate(layoutInflater, container, false)
        binding.dmPlayerWebView.load(studyEntity?.videoId)

        setTitle()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            textviewTitle.text = studyEntity?.pdfTitle
            buttonPractice.setOnClickListener {
                (activity as? NavigationActivity)?.findNavController(R.id.nav_host_fragment)
                    ?.navigate(R.id.action_descriptionFragment_to_quiz_fragment, arguments)
            }

            pdfCardView.setOnClickListener {
                (activity as? NavigationActivity)?.findNavController(R.id.nav_host_fragment)
                    ?.navigate(R.id.action_descriptionFragment_to_pdfFragment, arguments)
            }
        }
    }

    private fun setTitle() {
        val title = studyEntity?.name
        if (title != null)
            showTitle(title)
    }

    override fun onPause() {
        super.onPause()
        binding.dmPlayerWebView.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.dmPlayerWebView.onResume()
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        if (studyEntity?.videoId.isNullOrBlank()) {
//            val params = hashMapOf<String, String>()
//            binding.dmPlayerWebView.load("x7u1ql8", params)
//        }
//    }
}