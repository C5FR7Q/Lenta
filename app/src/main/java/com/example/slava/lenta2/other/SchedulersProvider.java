package com.example.slava.lenta2.other;

import io.reactivex.Scheduler;

/**
 * Created by slava on 01.10.2017.
 */

public interface SchedulersProvider {
    Scheduler io();
    Scheduler mainThread();
}
