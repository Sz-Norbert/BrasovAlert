package com.nika.brasovalert.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nika.brasovalert.db.UserEntity
import com.nika.brasovalert.remote.ReportResponse
import com.nika.brasovalert.repoitory.Repository
import com.nika.brasovalert.repoitory.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomefragmentViewModel @Inject constructor(val repository: Repository):ViewModel() {

    val _reoportsLiveData  = MutableLiveData<ReportResponse?>()
    val reortsLiveData : LiveData<ReportResponse?> =_reoportsLiveData
    val userLiveData: LiveData<UserEntity> = repository.getUser()
    fun getReports(token:String)=viewModelScope.launch{
        val resource= repository.getReports(token)
        if  (resource is Resource.Success && resource.data!=null){
            _reoportsLiveData.value=resource.data!!
        }else{
            _reoportsLiveData.value=null
        }

    }

}