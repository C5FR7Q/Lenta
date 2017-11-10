package com.example.slava.lenta2.repository;

import com.example.slava.lenta2.Util;
import com.example.slava.lenta2.TestSchedulerProvider;
import com.example.slava.lenta2.model.cache.Cache;
import com.example.slava.lenta2.model.data_client.DataDTO;
import com.example.slava.lenta2.model.data_client.LentaClient;
import com.example.slava.lenta2.other.DataListMapper;
import com.example.slava.lenta2.view.Data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by vva on 10/11/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public
class RepositoryTest
{
	@Mock
	private
	LentaClient mLentaClient;
	@Mock
	private
	Cache mCache;
	@Mock
	private DataListMapper mMapper;

	private Repository mRepository;

	@Before
	public
	void setUp() throws Exception {
		List<List<DataDTO>> listWithInternet = new ArrayList<>();
		listWithInternet.add(Util.getListDataDTO(mock(DataDTO.class)));
		listWithInternet.add(Util.getListDataDTO(mock(DataDTO.class)));
		listWithInternet.add(Util.getListDataDTO(mock(DataDTO.class)));

		List<List<Data>> listWithoutInternet = new ArrayList<>();
		listWithoutInternet.add(Util.getListData(mock(Data.class)));
		listWithoutInternet.add(Util.getListData(mock(Data.class)));
		listWithoutInternet.add(Util.getListData(mock(Data.class)));

		mRepository = new Repository(mLentaClient, mCache, mMapper, new TestSchedulerProvider());
		mRepository.setCompositeDisposable(new CompositeDisposable());

		when(mLentaClient.getLists()).thenReturn(Observable.just(listWithInternet));
		when(mCache.getDataList()).thenReturn(Observable.just(listWithoutInternet));
//		when(mMapper.apply(any())).thenReturn();
	}

	@Test
	public
	void getDataWithoutInternet() throws Exception {
		mRepository.getAllDataObservable(false);
		verify(mCache, only()).getDataList();
	}

	@Test
	public
	void getDataWithInternet() throws Exception {
		mRepository.getAllDataObservable(true);
		verify(mLentaClient, only()).getLists();
		verify(mCache, only()).putDataList(any());
	}

	@After
	public
	void tearDown() throws Exception {

	}
}