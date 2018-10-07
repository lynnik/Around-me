package com.lynnik.aroundme.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lynnik.aroundme.domain.Repository
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    val progressBarVisibility = MutableLiveData<Boolean>()
    val errorVisibility = MutableLiveData<String>()
    protected val repository = Repository
    protected val compositeDisposable = CompositeDisposable()

    protected fun showLoading() {
        progressBarVisibility.postValue(true)
    }

    protected fun hideLoading() {
        progressBarVisibility.postValue(false)
    }

    protected fun showError(message: String) {
        errorVisibility.postValue(message)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
        compositeDisposable.clear()
    }
}