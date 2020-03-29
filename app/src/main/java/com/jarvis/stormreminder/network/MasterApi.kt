package com.jarvis.stormreminder.network

import android.util.Log
import com.jarvis.stormreminder.model.Weather
import com.jarvis.stormreminder.network.`interface`.MasterService

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers


object MasterApi {
    private var mCompositedisposable: CompositeDisposable = CompositeDisposable()
    private var apiService: MasterService = MasterService.retrofitService()

    fun create(): MasterApi {
        return this
    }


    fun dispose() {
        mCompositedisposable.dispose()
    }

    fun getDeatil(
        dataType: String,
        language: String,
        onComplete: () -> Unit,
        onNext: (t: Weather) -> Unit,
        onError: (e: Throwable?) -> Unit
    ) {
        apiService.getDetail(dataType, language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Weather> {
                override fun onComplete() {
                    onComplete()
                }

                override fun onSubscribe(d: Disposable?) {
                    mCompositedisposable.add(d)
                }

                override fun onNext(t: Weather) {
                    onNext(t)
                }

                override fun onError(e: Throwable) {
                    onError(e)
                }

            })
    }
}