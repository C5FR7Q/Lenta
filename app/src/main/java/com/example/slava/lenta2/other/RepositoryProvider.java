package com.example.slava.lenta2.other;

import android.content.Context;

import com.example.slava.lenta2.model.cache.Cache;
import com.example.slava.lenta2.model.data_client.LentaClient;
import com.example.slava.lenta2.repository.DataRepository;
import com.example.slava.lenta2.repository.IDataRepository;

/**
 * Created by vva on 13/11/2017.
 */

public
class RepositoryProvider
{
	private static IDataRepository sDataRepository;

	public static
	void init(final Context context) {
		sDataRepository = new DataRepository(
				LentaClient.getInstance(),
				new Cache(context),
				new DataListMapper(),
				new PreExecuteSchedulerProvider()
		);
//		init other repositories, if need.
	}

	public static
	IDataRepository getDataRepository() {
		return sDataRepository;
	}
}
