package com.danielnascimento.bancodigital.presenter.auth.recover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.danielnascimento.bancodigital.R
import com.danielnascimento.bancodigital.databinding.FragmentRecoverAccountBinding
import com.danielnascimento.bancodigital.util.FirebaseHelper
import com.danielnascimento.bancodigital.util.StateView
import com.danielnascimento.bancodigital.util.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecoverAccountFragment : Fragment() {
    private var _binding: FragmentRecoverAccountBinding? = null
    private val binding get() = _binding!!

    private val recoverViewModel: RecoverViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRecoverAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnRecoverAccount.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val email = binding.inputEmail.text.toString().trim()

        if (email.isNotEmpty()) {
            recoverAccount(email)
        } else {
            binding.inputEmail.error = getString(R.string.text_email_empty)
        }
    }

    private fun recoverAccount(email: String) {
        recoverViewModel.recover(email).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is StateView.Success -> {
                    binding.progressBar.isVisible = false
                    showBottomSheet(message = getString(R.string.text_message_send_email_success))
                }

                is StateView.Error -> {
                    if (stateView.message!!.contains("email")) {
                        binding.inputEmail.error =
                            getString(FirebaseHelper.validError(stateView.message))
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