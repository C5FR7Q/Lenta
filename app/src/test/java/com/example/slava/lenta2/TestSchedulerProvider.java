package com.example.slava.lenta2;

import com.example.slava.lenta2.other.ISchedulerProvider;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by slava on 01.10.2017.
 */

public
class TestSchedulerProvider
		implements ISchedulerProvider
{
	@Override
	public
	Scheduler getScheduler() {
		return Schedulers.trampoline();
	}
}
