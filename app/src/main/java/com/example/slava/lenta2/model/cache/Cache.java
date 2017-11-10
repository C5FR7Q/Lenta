package com.example.slava.lenta2.model.cache;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.slava.lenta2.other.ISchedulerProvider;
import com.example.slava.lenta2.other.PreExecuteSchedulerProvider;
import com.example.slava.lenta2.view.Data;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by slava on 13.10.2017.
 */

public
class Cache
		implements ICache
{
	private static final String FILE_NAME = "news";

	private final File filePath;
	private final FileManager fileManager;
	private final Serializer serializer;
	private final ISchedulerProvider mSchedulerProvider;

	public
	Cache(@NonNull final Context context) {
		mSchedulerProvider = new PreExecuteSchedulerProvider();
		fileManager = new FileManager();
		serializer = new Serializer();
		filePath = buildFilePath(context.getCacheDir());
	}

	private
	File buildFilePath(final File cacheDir) {
		final String builder = cacheDir.getPath() +
		                       File.separator +
		                       FILE_NAME;
		return new File(builder);
	}

	@Override
	public
	Observable<List<List<Data>>> getDataList() {
		final Observable<List<List<Data>>> observable = Observable.create(e -> {
			final String string = fileManager.readFileContent(filePath);
			final List<List<Data>> data = serializer.deserialize(string);
			if (data != null) {
				e.onNext(data);
				e.onComplete();
			} else {
				e.onNext(Collections.emptyList());
				e.onComplete();
			}
		});
		return observable.subscribeOn(mSchedulerProvider.getScheduler());
	}

	@Override
	public
	Disposable putDataList(final List<List<Data>> data) {
		if (!data.isEmpty()) {
			final String json = serializer.serialize(data);
			return Observable.fromCallable(new Writer(json, fileManager, filePath))
					.subscribeOn(mSchedulerProvider.getScheduler())
					.subscribe(aVoid -> {
					}, throwable -> {
					});
			/*Важно, чтобы при ошибке ничего не происходило. Дело в том, что Callable<Void>
			* в методе call должен содержать return. Вернуть можно только null. Тогда вышестоящая
            * цепочка без указания throwable -> {} выбрасывает "Callable returned null"*/
		}
		return null;
	}

	private static
	class Writer
			implements Callable<Void>
	{
		private final String json;
		private final FileManager fileManager;
		private final File file;

		private
		Writer(final String json, final FileManager fileManager, final File file) {
			this.json = json;
			this.fileManager = fileManager;
			this.file = file;
		}

		@Override
		public
		Void call() throws Exception {
			fileManager.writeToFile(file, json);
			return null;
		}
	}
}
