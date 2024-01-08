package com.nika.brasovalert.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nika.brasovalert.R
import com.nika.brasovalert.databinding.FragmentRegisterBinding
import com.nika.brasovalert.mvvm.RegisterViewModel
import com.nika.brasovalert.remote.RegisterBody
import com.nika.brasovalert.repoitory.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment():Fragment(R.layout.fragment_register) {

    lateinit var binding: FragmentRegisterBinding
    val vm by viewModels<RegisterViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onRegisterPressed()
        observeIsRegistred()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun onRegisterPressed() {
        binding.btRegister.setOnClickListener {
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val email = binding.etEmail.text.toString()
            val password: String = binding.etPassword.text.toString()
            val confimPassoword = binding.etConfirmPassword.text.toString()
            if (password.equals(confimPassoword)) {
                val user = RegisterBody(confimPassoword, email, firstName, lastName, password)
                vm.registerUser(user)
            } else {
                Toast.makeText(requireContext(), "the password is not the same", Toast.LENGTH_SHORT)
                    .show()
            }
        }


    }


    private fun observeIsRegistred() {
        vm.registerResponseLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "${it.data?.message}", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment2_to_logginFragment3)
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}