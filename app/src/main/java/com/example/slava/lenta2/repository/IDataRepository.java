package com.example.slava.lenta2.repository;

import com.example.slava.lenta2.view.Data;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by vva on 09/11/2017.
 */

public
interface IDataRepository
{
	Observable<List<List<Data>>> getAllDataObservable(boolean hasInternetConnection);
	void setCompositeDisposable(CompositeDisposable disposable);
}
