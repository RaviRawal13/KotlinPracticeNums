package com.foobles.kotlinnum.home.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foobles.kotlinnum.home.repo.MainRepo
import com.foobles.kotlinnum.utils.doExecution
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel : ViewModel() {
    private val mainRepo = MainRepo()
    val triviaData = MutableLiveData("")
    fun fetchRandomTrivia() {
        viewModelScope.launch {
            mainRepo.fetchRandomTrivia().doExecution {
                triviaData.value = try {
                    it
                } catch (e: Exception) {
                    ""
                }
            }
        }
    }

}
