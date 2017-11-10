package com.example.slava.lenta2.other;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by slava on 01.10.2017.
 */

public
class PostExecuteSchedulerProvider
		implements ISchedulerProvider
{
	@Override
	public
	Scheduler getScheduler() {
		return AndroidSchedulers.mainThread();
	}
}
