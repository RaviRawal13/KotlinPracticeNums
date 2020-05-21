package com.foobles.kotlinnum.storage.vm

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foobles.kotlinnum.storage.repo.StorageRepo
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.ListResult
import kotlinx.coroutines.launch

class StorageViewModel : ViewModel() {
    private val storageRepo = StorageRepo()

    var filesList: MediatorLiveData<ListResult>? = MediatorLiveData()

    fun fetchFiles(currentUser: FirebaseUser?) {
        viewModelScope.launch {
            val listResult = storageRepo.strategiesList(currentUser)
            filesList?.value = listResult
        }
    }

}