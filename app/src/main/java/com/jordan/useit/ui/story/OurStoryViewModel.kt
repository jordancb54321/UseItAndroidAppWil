package com.jordan.useit.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OurStoryViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Our Story Fragment"
    }
    val text: LiveData<String> = _text
} 