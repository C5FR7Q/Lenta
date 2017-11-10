package com.example.slava.lenta2.other;

import io.reactivex.Scheduler;

/**
 * Created by vva on 10/11/2017.
 */

public
interface ISchedulerProvider
{
	Scheduler getScheduler();
}
