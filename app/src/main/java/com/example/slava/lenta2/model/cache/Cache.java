package com.example.slava.lenta2.model.cache;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.slava.lenta2.other.ISchedulerProvider;
import com.example.slava.lenta2.other.PreExecuteSchedulerProvider;
import com.example.slava.lenta2.view.Data;

import java.io.File;
import java.util.Collections;
import java.util.List;

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
	Cache(@NonNull final File cacheDir) {
		mSchedulerProvider = new PreExecuteSchedulerProvider();
		fileManager = new FileManager();
		serializer = new Serializer();
		filePath = buildFilePath(cacheDir);
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
		Log.d("Cache", "GET IT FROM CACHE");
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
			return Observable.just(new Writer(json, fileManager, filePath))
					.observeOn(mSchedulerProvider.getScheduler())
					.subscribe(Writer::write);
		}
		return null;
	}

	private static
	class Writer
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

		private
		void write() {
			fileManager.writeToFile(file, json);
		}
	}
}
