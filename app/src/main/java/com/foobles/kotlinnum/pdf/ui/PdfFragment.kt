package com.foobles.kotlinnum.pdf.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.foobles.kotlinnum.BaseFragment
import com.foobles.kotlinnum.databinding.PdfViewFragmentBinding
import com.foobles.kotlinnum.entity.StudyEntity
import com.foobles.kotlinnum.utils.ARG_STUDY_ENTITY


class PdfFragment : BaseFragment() {
    private lateinit var binding: PdfViewFragmentBinding
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
        showToolbar()
        binding = PdfViewFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgressBar()
        binding.webView.settings.setSupportZoom(true)
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(studyEntity?.pdfLink)

        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                // do your stuff here
                hideProgressBar()
            }
        }
    }
}