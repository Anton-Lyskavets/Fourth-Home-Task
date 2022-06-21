package com.example.fourth_home_task.model

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fourth_home_task.network.BankATM
import com.example.fourth_home_task.network.BankApi
import kotlinx.coroutines.launch

class BankViewModel : ViewModel() {
    private val _bankATM = MutableLiveData<List<BankATM>>()
    val bankATM: LiveData<List<BankATM>> = _bankATM

    init {
        getBankATM()
    }

    @SuppressLint("NullSafeMutableLiveData")
    private fun getBankATM() {
        viewModelScope.launch {
            _bankATM.value = BankApi.retrofitService.getATM()
        }
    }
}