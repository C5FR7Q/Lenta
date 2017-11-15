package com.example.slava.lenta2.other;

import android.content.Context;

import com.example.slava.lenta2.model.cache.Cache;
import com.example.slava.lenta2.model.data_client.LentaClient;
import com.example.slava.lenta2.repository.DataRepository;
import com.example.slava.lenta2.repository.IDataRepository;
import com.example.slava.lenta2.view.Data;

import java.util.List;

/**
 * Created by vva on 13/11/2017.
 */

public
class RepositoryProvider
{
	private static RepositoryProvider instance;
	private final IDataRepository<List<List<Data>>> sDataRepository;

	private
	RepositoryProvider(final IDataRepository<List<List<Data>>> sDataRepository) {
		this.sDataRepository = sDataRepository;
//		init other repositories, if need.
	}

	public static
	void init(final Context context) {
		final DataRepository sDataRepository = new DataRepository(
				LentaClient.getInstance(),
				new Cache(context.getCacheDir()),
				new DataListMapper(),
				new PreExecuteSchedulerProvider()
		);
		instance = new RepositoryProvider(sDataRepository);
	}

	public static
	RepositoryProvider getInstance() {
		return instance;
	}

	public
	IDataRepository<List<List<Data>>> getDataRepository() {
		return sDataRepository;
	}
}
