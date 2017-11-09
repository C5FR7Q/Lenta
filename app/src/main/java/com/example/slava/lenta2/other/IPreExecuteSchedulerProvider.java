package com.example.slava.lenta2.other;

import io.reactivex.Scheduler;

/**
 * Created by slava on 11.10.2017.
 */

public
interface IPreExecuteSchedulerProvider
{
	Scheduler getScheduler();
}
