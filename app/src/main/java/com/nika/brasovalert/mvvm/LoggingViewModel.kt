package com.nika.brasovalert.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nika.brasovalert.db.UserEntity
import com.nika.brasovalert.remote.AuthBody
import com.nika.brasovalert.remote.AuthResponse
import com.nika.brasovalert.repoitory.Repository
import com.nika.brasovalert.repoitory.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoggingViewModel @Inject constructor(private val repository: Repository):ViewModel() {

    private val _userDetail = MutableLiveData<AuthResponse?>()
    val userDetail : LiveData<AuthResponse?> = _userDetail







    fun authUser(user: AuthBody)=viewModelScope.launch {
        val resource = repository.authUser(user)
        if (resource is Resource.Success && resource.data != null) {
            _userDetail.value=resource.data!!

            repository.setIsLogged(true)
            repository.setEmail(resource.data.data.email)

        }else{
            _userDetail.value=null

        }
    }

    fun insertUser(user:UserEntity) = viewModelScope.launch {
        repository.insertUser(user)
    }


}