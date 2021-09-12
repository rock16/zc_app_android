package com.tolstoy.zurichat.ui.login.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tolstoy.zurichat.R
import com.tolstoy.zurichat.databinding.FragmentLoginBinding
import com.tolstoy.zurichat.databinding.FragmentSignupBinding
import com.tolstoy.zurichat.ui.login.LoggedInUserView
import com.tolstoy.zurichat.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView = binding.textViewRegister
        val email = binding.loginEmail
        val password = binding.loginPassword
        val login = binding.buttonLogin

        loginViewModel.loginResult.observe(viewLifecycleOwner, Observer {
            val loginResult = it ?: return@Observer

            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null){
                updateUiWithUser(loginResult.success)
                // TODO transition to other screen
            }
        })

        login.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                loginViewModel.login(email.text.toString(), password.text.toString())
            }
        }

        textView.setOnClickListener(fun(it: View){
            findNavController().navigate(R.id.signupFragment)
        })
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = "Welcome"
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            requireContext().applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(requireContext().applicationContext, errorString, Toast.LENGTH_SHORT).show()

    }

}