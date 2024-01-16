package com.nika.brasovalert.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nika.brasovalert.db.UserEntity
import com.nika.brasovalert.remote.CreateReportBody
import com.nika.brasovalert.remote.CreateReportResponse
import com.nika.brasovalert.repoitory.Repository
import com.nika.brasovalert.repoitory.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CreateReportViewModel @Inject constructor (private val repository: Repository):ViewModel() {




   private val _reportResponseLiveData = MutableLiveData<Resource<CreateReportResponse>> ()
    val reportResponseLiveData :LiveData<Resource<CreateReportResponse>> =_reportResponseLiveData


    fun postReport(createReportBody: CreateReportBody)=viewModelScope.launch {
       val resource = repository.createReport(createReportBody)
        _reportResponseLiveData.value=resource
    }

    fun getUserDetail(email:String):LiveData<UserEntity>{
       return repository.getUser(email)
    }
}
