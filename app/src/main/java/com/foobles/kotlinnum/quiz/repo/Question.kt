package com.foobles.kotlinnum.quiz.repo

import java.util.*
import kotlin.math.abs

/**
 * The data model.
 */

const val QUESTION_TYPE_SUM = 0
const val QUESTION_TYPE_SUB = 1
const val QUESTION_TYPE_MUL = 2
const val QUESTION_TYPE_DIV = 3
const val QUESTION_TYPE_MUL_BY_11 = 4
const val QUESTION_TYPE_MUL_BY_25 = 5
const val QUESTION_TYPE_MUL_BY_75 = 6
const val QUESTION_TYPE_LOIF = 7


class Question(val questionType: Int = QUESTION_TYPE_SUM) {
    // Getters
    // A question has 2 addends (left term and right term),
    // a list of possible answers, and a correct answer.
    var leftAddend: Int = 0
    var rightAddend: Int = 0
    var correctAnswer: Int = 0
    var answers: List<Int>

    private fun genAns(): Int {
        return when (questionType) {
            QUESTION_TYPE_SUB -> leftAddend - rightAddend
            QUESTION_TYPE_MUL, QUESTION_TYPE_MUL_BY_11, QUESTION_TYPE_MUL_BY_25, QUESTION_TYPE_MUL_BY_75 -> leftAddend * rightAddend
            QUESTION_TYPE_DIV -> leftAddend / rightAddend
            else -> leftAddend + rightAddend
        }
    }

    init {
        // Used to store the possible answers to the question - they must be unique.
        val answersSet: MutableSet<Int> = HashSet()
        val random = Random()

        // Randomly generate the left term of addition.
        leftAddend = random.nextInt(90) + 1

        rightAddend = when (questionType) {
            QUESTION_TYPE_MUL_BY_11 -> 11
            QUESTION_TYPE_MUL_BY_25 -> 11
            QUESTION_TYPE_MUL_BY_75 -> 11
            else -> {
                random.nextInt(90) + 1
            }
        }

        // Store the correct answer.
        correctAnswer = genAns()
        answersSet.add(correctAnswer)

        // Define the ranges used to generate the incorrect answers.
        val low = Integer.max(leftAddend, rightAddend) + Integer.min(
            leftAddend,
            rightAddend
        ) / 2
        val high = correctAnswer + Integer.min(leftAddend, rightAddend) / 2
        // Generate the incorrect answers.
        do {
            answersSet.add(random.nextInt(abs(high - low)) + low)
        } while (answersSet.size < 3)

        // Randomly permute the list of possible answers.
        answers = ArrayList(answersSet)
        Collections.shuffle(answers)
    }
}