package com.foobles.kotlinnum

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.foobles.kotlinnum.nav.NavigationActivity

open class BaseFragment : Fragment() {

    private var progressBar: ProgressBar? = null

    fun hideToolbar() {
        (activity as NavigationActivity).toolbar.visibility = View.GONE
    }

    fun showToolbar() {
        (activity as NavigationActivity).toolbar.visibility = View.VISIBLE
    }

    fun showTitle(title: String) {
        (activity as NavigationActivity).toolbar.title = title
    }

    fun setProgressBar(bar: ProgressBar) {
        progressBar = bar
    }

    fun showProgressBar() {
        progressBar?.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        progressBar?.visibility = View.INVISIBLE
    }

    fun hideKeyboard(view: View) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onStop() {
        super.onStop()
        hideProgressBar()
    }
}
