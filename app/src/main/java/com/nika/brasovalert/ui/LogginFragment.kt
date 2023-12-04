package com.nika.brasovalert.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nika.brasovalert.R
import com.nika.brasovalert.databinding.FragemntLogginBinding

class LogginFragment():Fragment(R.layout.fragemnt_loggin) {

    lateinit var binding: FragemntLogginBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onRegisterPress()
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

}