package com.example.slava.lenta2.other;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by slava on 11.10.2017.
 */

public class PreExecuteSchedulerProvider implements IPreExecuteSchedulerProvider {
    @Override
    public Scheduler getScheduler() {
        return Schedulers.io();
    }
}
