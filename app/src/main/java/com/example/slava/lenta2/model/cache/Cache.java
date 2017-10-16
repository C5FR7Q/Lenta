package com.example.slava.lenta2.model.cache;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.slava.lenta2.other.IPreExecuteSchedulerProvider;
import com.example.slava.lenta2.other.PreExecuteSchedulerProvider;
import com.example.slava.lenta2.view.Data;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by slava on 13.10.2017.
 */

public class Cache implements ICache {
    public static final String FILE_NAME = "news";

    private File filePath;
    private FileManager fileManager;
    private Serializer serializer;
    private IPreExecuteSchedulerProvider preExecuteSchedulerProvider;

    public Cache(@NonNull Context context) {
        this.preExecuteSchedulerProvider = new PreExecuteSchedulerProvider();
        this.fileManager = new FileManager();
        this.serializer = new Serializer();
        filePath = buildFilePath(context.getCacheDir());
    }

    private File buildFilePath(File cacheDir) {
        StringBuilder builder = new StringBuilder();
        builder.append(cacheDir.getPath())
                .append(File.separator)
                .append(FILE_NAME);
        return new File(builder.toString());
    }

    @Override
    public Observable<List<List<Data>>> getDataList() {
        Observable<List<List<Data>>> observable = Observable.create(e -> {
            String string = fileManager.readFileContent(filePath);
            List<List<Data>> data = serializer.deserialize(string);
            if (data != null) {
                e.onNext(data);
                e.onComplete();
            } else e.onError(new Exception("Empty cache."));
        });
        return observable.subscribeOn(preExecuteSchedulerProvider.getScheduler());
    }

    @Override
    public Disposable putDataList(List<List<Data>> data) {
        if (!data.isEmpty()){
            String json = serializer.serialize(data);
            return Observable.fromCallable(new Writer(json, fileManager, filePath))
                    .subscribeOn(preExecuteSchedulerProvider.getScheduler())
                    .subscribe(aVoid -> {},throwable -> {});
            /*Важно, чтобы при ошибке ничего не происходило. Дело в том, что Callable<Void>
            * в методе call должен содержать return. Вернуть можно только null. Тогда вышестоящая
            * цепочка без указания throwable -> {} выбрасывает "Callable returned null"*/
        }
        return null;
    }

    private static class Writer implements Callable<Void> {
        private String json;
        private FileManager fileManager;
        private File file;

        public Writer(String json, FileManager fileManager, File file) {
            this.json = json;
            this.fileManager = fileManager;
            this.file = file;
        }

        @Override
        public Void call() throws Exception {
            fileManager.writeToFile(file, json);
            return null;
        }
    }
}