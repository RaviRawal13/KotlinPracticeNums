package com.foobles.kotlinnum.login.ui

import android.animation.Animator
import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.foobles.kotlinnum.BaseFragment
import com.foobles.kotlinnum.R
import com.foobles.kotlinnum.databinding.LoginFragmentBinding
import com.foobles.kotlinnum.login.vm.LoginViewModel
import com.foobles.kotlinnum.nav.NavigationActivity

class LoginFragment : BaseFragment() {

    private val loginViewModel by activityViewModels<LoginViewModel>()
    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(layoutInflater, container, false)
        hideToolbar()
        setProgressBar(binding.loadingProgressBar)
        val currentUser = loginViewModel.getCurrentUser()

        object : CountDownTimer(3000, 1000) {
            override fun onFinish() {
                binding.bookITextView.visibility = View.GONE
                binding.loadingProgressBar.visibility = View.GONE
                context?.let {
                    binding.rootView.setBackgroundColor(
                        ContextCompat.getColor(
                            it,
                            R.color.color_primary_light
                        )
                    )
                }
                binding.bookIconImageView.setImageResource(R.drawable.ic_learning_invert)
                startAnimation()
            }

            override fun onTick(p0: Long) {}
        }.start()
        return binding.rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loginViewModel.authData?.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val user = loginViewModel.getCurrentUser()
                val email = user?.email ?: ""
                Toast.makeText(
                    context, "Hello $email.",
                    Toast.LENGTH_SHORT
                ).show()
                loginViewModel.email = email

            }
            (activity as? NavigationActivity)?.findNavController(R.id.nav_host_fragment)
                ?.navigate(R.id.action_login_fragment_to_levelFragment)
            hideProgressBar()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registerButton.setOnClickListener {
            showProgressBar()
            loginViewModel.signUp(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        binding.loginButton.setOnClickListener {
            showProgressBar()
            loginViewModel.signIn(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }
    }

    private fun startAnimation() {
        binding.bookIconImageView.animate().apply {
            x(50f)
            y(100f)
            duration = 1000
        }.setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {}

            override fun onAnimationEnd(p0: Animator?) {
                binding.afterAnimationView.visibility = VISIBLE
            }

            override fun onAnimationCancel(p0: Animator?) {}

            override fun onAnimationStart(p0: Animator?) {}
        })
    }
}

