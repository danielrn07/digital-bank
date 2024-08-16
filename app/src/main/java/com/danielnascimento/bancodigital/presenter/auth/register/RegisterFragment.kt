package com.danielnascimento.bancodigital.presenter.auth.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.danielnascimento.bancodigital.R
import com.danielnascimento.bancodigital.data.model.User
import com.danielnascimento.bancodigital.databinding.FragmentRegisterBinding
import com.danielnascimento.bancodigital.util.FirebaseHelper
import com.danielnascimento.bancodigital.util.StateView
import com.danielnascimento.bancodigital.util.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnRegister.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val name = binding.inputName.text.toString().trim()
        val email = binding.inputEmail.text.toString().trim()
        val phone = binding.inputPhone.text.toString().trim()
        val password = binding.inputPassword.text.toString().trim()

        if (name.isNotEmpty()) {
            if (email.isNotEmpty()) {
                if (phone.isNotEmpty()) {
                    if (password.isNotEmpty()) {
                        binding.progressBar.isVisible = true

                        val user = User(name, email, phone, password)
                        registerUser(user)
                    } else {
                        binding.inputPassword.error = getString(R.string.text_passoword_empty)
                    }
                } else {
                    binding.inputPhone.error = getString(R.string.text_phone_empty)
                }
            } else {
                binding.inputEmail.error = getString(R.string.text_email_empty)
            }
        } else {
            binding.inputName.error = getString(R.string.text_name_empty)
        }
    }

    private fun registerUser(user: User) {
        registerViewModel.register(user).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is StateView.Success -> {
                    binding.progressBar.isVisible = false
                    findNavController().navigate(R.id.action_global_homeFragment)
                }

                is StateView.Error -> {
                    if (stateView.message!!.contains("email")) {
                        binding.inputEmail.error =
                            getString(FirebaseHelper.validError(stateView.message))
                    } else if (stateView.message!!.contains(("password"))) {
                        binding.inputPassword.error =
                            getString(FirebaseHelper.validError(stateView.message ?: ""))
                    } else {
                        showBottomSheet(
                            message = getString(
                                FirebaseHelper.validError(
                                    stateView.message ?: ""
                                )
                            )
                        )
                    }

                    binding.progressBar.isVisible = false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}