package com.example.slava.lenta2;

import com.example.slava.lenta2.other.SchedulersProvider;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by slava on 01.10.2017.
 */

public class TestSchedulersProvider implements SchedulersProvider{
    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler mainThread() {
        return Schedulers.trampoline();
    }
}
