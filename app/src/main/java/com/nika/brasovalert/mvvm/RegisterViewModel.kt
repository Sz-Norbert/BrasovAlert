package com.nika.brasovalert.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nika.brasovalert.remote.AuthBody
import com.nika.brasovalert.remote.AuthResponse
import com.nika.brasovalert.remote.RegisterBody
import com.nika.brasovalert.remote.RegisterResponse

import com.nika.brasovalert.repoitory.Repositroy
import com.nika.brasovalert.repoitory.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class RegisterViewModel @Inject constructor(val repository: Repositroy) : ViewModel() {

    private val _registerResponseLiveData = MutableLiveData<Resource<RegisterResponse>>()
    val registerResponseLiveData: LiveData<Resource <RegisterResponse>> = _registerResponseLiveData



    fun registerUser(user: RegisterBody) = viewModelScope.launch {

        val resource  = repository.registerUser(user)
        _registerResponseLiveData.value=resource
    }





}
