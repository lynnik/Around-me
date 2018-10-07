package com.lynnik.aroundme.extensions

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.bind(compositeDisposable: CompositeDisposable): Disposable {
    compositeDisposable.add(this)
    return this
}