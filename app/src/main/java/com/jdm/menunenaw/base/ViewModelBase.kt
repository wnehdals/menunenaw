package com.jdm.menunenaw.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineExceptionHandler

open class ViewModelBase : ViewModel() {
    private val TAG = ViewModelBase::class.simpleName
    protected val compositeDisposable = CompositeDisposable()
    protected val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG,"ExceptionHandler : $throwable")
    }
    val isLoading = MutableLiveData<Boolean>()

    override fun onCleared() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
        super.onCleared()
    }
}
