package com.example.slava.lenta2.repository;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by vva on 09/11/2017.
 */

public
interface IDataRepository<T>
	/*Not sure, that there should be generics of T. May be <T extends<List<T>>> */
{
	Observable<T> getData(boolean hasInternetConnection);
	void setCompositeDisposable(CompositeDisposable disposable);
}
