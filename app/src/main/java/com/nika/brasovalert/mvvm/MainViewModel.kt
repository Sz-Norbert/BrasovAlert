package com.nika.brasovalert.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nika.brasovalert.repoitory.Repository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository):ViewModel() {

    fun observeIsLogged(): LiveData<Boolean> {

        return repository.isLogged

    }
}