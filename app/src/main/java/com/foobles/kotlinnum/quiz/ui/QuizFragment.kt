package com.foobles.kotlinnum.quiz.ui

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.foobles.kotlinnum.BaseFragment
import com.foobles.kotlinnum.databinding.QuizFragmentBinding
import com.foobles.kotlinnum.quiz.repo.*
import com.foobles.kotlinnum.utils.*

class QuizFragment : BaseFragment() {
    private lateinit var binding: QuizFragmentBinding

    private var mQuestion: Question? = null

    var questionType = QUESTION_TYPE_SUM
    var preferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        questionType = arguments?.getInt(ARG_QUESTION_TYPE) ?: QUESTION_TYPE_SUM
    }

    private fun selectPref(questionType: Int): Pair<String, String> {
        return when (questionType) {
            QUESTION_TYPE_SUB -> {
                Pair(CORRECT_SUB, TOTAL_SUB)
            }
            QUESTION_TYPE_MUL -> {
                Pair(CORRECT_MUL, TOTAL_MUL)
            }
            QUESTION_TYPE_DIV -> {
                Pair(CORRECT_DIV, TOTAL_DIV)
            }
            QUESTION_TYPE_MUL_BY_11 -> {
                Pair(CORRECT_MUL_11, TOTAL_MUL_11)
            }
            QUESTION_TYPE_MUL_BY_25 -> {
                Pair(CORRECT_MUL_25, TOTAL_MUL_25)
            }
            QUESTION_TYPE_MUL_BY_75 -> {
                Pair(CORRECT_MUL_75, TOTAL_MUL_75)
            }
            else -> {
                Pair(CORRECT_SUM, TOTAL_SUM)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = QuizFragmentBinding.inflate(layoutInflater, container, false)
        return binding.rootLayout
    }

    @SuppressLint("ApplySharedPref")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = context?.getSharedPreferences("prefs", 0)
        val prefPair = selectPref(questionType)
        showToolbar()

        generateNewQuestion()

        binding.submitButton.setOnClickListener {
            val selectedAnswerId: Int = binding.toggleButton.checkedButtonId
            val message = if (selectedAnswerId == -1) {
                "Select An Answer!"
            } else {
                val selectedButton: Button = binding.toggleButton.findViewById(selectedAnswerId)
                val correctAnswer: String = mQuestion?.correctAnswer.toString()
                val message = if (selectedButton.text == correctAnswer) {
                    val count = preferences?.getInt(prefPair.first, 0) ?: 0
                    val newCount = count + 1
                    preferences?.edit()?.putInt(prefPair.first, newCount)?.apply()
                    "Correct!"
                } else {
                    "Incorrect."
                }
                val tCount = preferences?.getInt(prefPair.second, 0) ?: 0
                val newTCount = tCount + 1
                preferences?.edit()?.putInt(prefPair.second, newTCount)?.apply()
                generateNewQuestion()
                message

            }
            val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.setMargin(0f, 0.2f)
            toast.show()
        }
    }

    private fun generateNewQuestion() {
        with(binding) {
            toggleButton.clearChecked()
            mQuestion = Question(questionType).apply {
                textViewLeftAddend.text = leftAddend.toString()
                textViewRightAddend.text = rightAddend.toString()
                firstButton.text = answers[0].toString()
                secondButton.text = answers[1].toString()
                thirdButton.text = answers[2].toString()

                when (questionType) {
                    QUESTION_TYPE_SUB -> operatorType.text = "-"
                    QUESTION_TYPE_MUL, QUESTION_TYPE_MUL_BY_11, QUESTION_TYPE_MUL_BY_25, QUESTION_TYPE_MUL_BY_75 -> operatorType.text =
                        "*"
                    QUESTION_TYPE_DIV -> operatorType.text = "/"
                    else -> operatorType.text = "+"
                }
            }
        }
    }
}