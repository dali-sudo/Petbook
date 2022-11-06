package com.example.petbook.viewModel

interface loginListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}