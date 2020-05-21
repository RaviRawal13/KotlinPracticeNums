package com.foobles.kotlinnum.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.foobles.kotlinnum.BaseFragment
import com.foobles.kotlinnum.R
import com.foobles.kotlinnum.databinding.MainFragmentBinding
import com.foobles.kotlinnum.entity.StudyEntity
import com.foobles.kotlinnum.home.vm.MainViewModel
import com.foobles.kotlinnum.nav.NavigationActivity
import com.foobles.kotlinnum.quiz.repo.*
import com.foobles.kotlinnum.utils.ARG_QUESTION_TITLE
import com.foobles.kotlinnum.utils.ARG_QUESTION_TYPE
import com.foobles.kotlinnum.utils.ARG_STUDY_ENTITY
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.lang.reflect.Type
import java.nio.charset.Charset


class MainFragment : BaseFragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    var studyEntityArrayList: ArrayList<StudyEntity>? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showToolbar()
        studyEntityArrayList = loadJSONFromAsset()

        with(binding) {
            buttonAdd.setOnClickListener {
                navigate(QUESTION_TYPE_SUM, buttonAdd.text)
            }
            buttonSub.setOnClickListener {
                navigate(QUESTION_TYPE_SUB, buttonSub.text)
            }
            buttonMul.setOnClickListener {
                navigate(QUESTION_TYPE_MUL, buttonMul.text)
            }
            buttonDiv.setOnClickListener {
                navigate(QUESTION_TYPE_DIV, buttonDiv.text)
            }
            buttonRandom.setOnClickListener {
                val random = (0..3).shuffled().first()
                (activity as? NavigationActivity)?.findNavController(R.id.nav_host_fragment)
                    ?.navigate(R.id.action_nav_study_to_quiz_fragment, Bundle().apply {
                        putInt(ARG_QUESTION_TYPE, random)
                    })
            }
            buttonMulBy11.setOnClickListener {
                navigate(QUESTION_TYPE_MUL_BY_11, buttonMulBy11.text)
            }
            buttonMulBy25.setOnClickListener {
                navigate(QUESTION_TYPE_MUL_BY_25, buttonMulBy25.text)
            }
            buttonMulBy75.setOnClickListener {
                navigate(QUESTION_TYPE_MUL_BY_75, "Multiplication by 75")
            }
            buttonLoif.setOnClickListener {
                Snackbar.make(
                    view,
                    "Paid feature! Contact us to get this feature.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun navigate(questionType: Int, text: CharSequence) {
        val studyEntity = studyEntityArrayList?.find { it.questionType == questionType }
        (activity as? NavigationActivity)?.findNavController(R.id.nav_host_fragment)
            ?.navigate(R.id.action_nav_study_to_descriptionFragment, Bundle().apply {
                putParcelable(ARG_STUDY_ENTITY, studyEntity)
                putString(ARG_QUESTION_TITLE, text.toString())
                putInt(ARG_QUESTION_TYPE, questionType)
            })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun loadJSONFromAsset(): ArrayList<StudyEntity>? {
        //function to load the JSON from the Asset and return the object
        var json: String? = null
        val charset: Charset = Charsets.UTF_8
        try {
            val inputStream = context?.assets?.open("study.json")
            if (inputStream != null) {
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                json = String(buffer, charset)
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        val studyEntity: Type = object : TypeToken<ArrayList<StudyEntity?>?>() {}.type

        return Gson().fromJson(json, studyEntity)
    }
}
