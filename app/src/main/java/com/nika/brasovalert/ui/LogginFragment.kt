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
import com.nika.brasovalert.databinding.FragemntLogginBinding
import com.nika.brasovalert.db.UserEntity
import com.nika.brasovalert.mvvm.LoggingViewModel
import com.nika.brasovalert.remote.AuthBody
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogginFragment():Fragment(R.layout.fragemnt_loggin) {

    lateinit var binding: FragemntLogginBinding
    val vm by viewModels<LoggingViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onRegisterPress()
        onLogginPress()
        observeUser()
    }

    private fun onLogginPress() {
        binding.btLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password=binding.etPassword.text.toString()
            val user = AuthBody(email,password)
            vm.authUser(user)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragemntLogginBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    private fun onRegisterPress() {
        binding.btRegister.setOnClickListener {

            findNavController().navigate(R.id.action_logginFragment3_to_registerFragment2)
        }
    }

    private fun observeUser(){
        vm.userDetail.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                val user = UserEntity(it.data.token, it.data.firstName,it.data.lastName,it.data.email)
                vm.insertUser(user)
                Toast.makeText(requireContext(), "successful logged", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_logginFragment3_to_homeFragment)
            }
            else{
                Toast.makeText(requireContext(), "no user found", Toast.LENGTH_SHORT).show()
            }
        })
    }

}