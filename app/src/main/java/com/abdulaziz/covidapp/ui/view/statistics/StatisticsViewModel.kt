package com.abdulaziz.covidapp.ui.view.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(): ViewModel() {

    private val _updateText = MutableLiveData<String>().apply {
        value = ""
    }

    fun setLastUpdate(str: String) {
        val updateStr = if (str.length > 10) str.substring(0, 10) else str
        _updateText.value = "Last update $updateStr"
    }

    val updateText: LiveData<String> = _updateText
}