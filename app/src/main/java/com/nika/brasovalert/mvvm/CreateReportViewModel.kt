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
class CreateReportViewModel @Inject constructor (val repository: Repository):ViewModel() {



    val userLiveData: LiveData<UserEntity> = repository.getUser()
    val _reportResponseLiveData = MutableLiveData<Resource<CreateReportResponse>> ()
    val reportResponseLiveData :LiveData<Resource<CreateReportResponse>> =_reportResponseLiveData


    fun postReport(createReportBody: CreateReportBody)=viewModelScope.launch {
       val resource = repository.createReport(createReportBody)
        _reportResponseLiveData.value=resource
    }
}
