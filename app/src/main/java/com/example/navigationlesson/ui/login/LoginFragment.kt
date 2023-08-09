package com.example.navigationlesson.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.navigationlesson.R
import com.example.navigationlesson.data.database.Database
import com.example.navigationlesson.data.state.LoginState
import com.example.navigationlesson.databinding.FragmentLoginBinding
import com.example.navigationlesson.showToast
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginFragment: Fragment(R.layout.fragment_login) {


    lateinit var binding:FragmentLoginBinding
    private val viewModel:LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)



        observeLoginState()

        binding.btnLogin.setOnClickListener {
          viewModel.login(Database.getDatabase(requireContext()),binding.etEmail.text.toString(),binding.etPassword.text.toString())
        }



    }

    private fun observeLoginState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.loginState.collect{
                    when(it){
                        is LoginState.Idle->{}
                        is LoginState.Loading->{}
                        is LoginState.UserNotFound->{
                            requireActivity().showToast("Kullanici Bulunamadi")
                        }
                        is LoginState.Success->{
                            findNavController().navigate(R.id.action_loginFragment_to_newsFragment)
                            requireActivity().showToast("Hosgeldiniz ${it.user.name}")

                        }
                        is LoginState.Error->{
                            requireActivity().showToast("Bir sorun olustu")
                        }
                    }
                }
            }
        }
    }
}