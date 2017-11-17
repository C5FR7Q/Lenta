package com.example.slava.lenta2.repository;

import com.example.slava.lenta2.TestSchedulerProvider;
import com.example.slava.lenta2.Util;
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
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

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
	private LentaClient mLentaClient;
	@Mock
	private Cache mCache;

	private DataRepository mRepository;

	@Before
	public
	void setUp() throws Exception {
		final List<List<DataDTO>> listWithInternet = new ArrayList<>();
		listWithInternet.add(Util.getListDataDTO(Util.getDataDTO(), Util.getDataDTO()));
		listWithInternet.add(Util.getListDataDTO(Util.getDataDTO(), Util.getDataDTO()));
		listWithInternet.add(Util.getListDataDTO(Util.getDataDTO(), Util.getDataDTO()));

		final List<List<Data>> listWithoutInternet = new ArrayList<>();
		listWithoutInternet.add(Util.getListData(Util.getData(), Util.getData()));
		listWithoutInternet.add(Util.getListData(Util.getData(), Util.getData()));
		listWithoutInternet.add(Util.getListData(Util.getData(), Util.getData()));

		mRepository = new DataRepository(mLentaClient, mCache, new DataListMapper(), new TestSchedulerProvider());

		when(mLentaClient.getLists()).thenReturn(Observable.just(listWithInternet));
		when(mCache.getDataList()).thenReturn(Observable.just(listWithoutInternet));
		when(mCache.putDataList(any())).thenReturn(Observable.just(0).subscribe());
	}

	@Test
	public
	void getDataWithoutInternet() throws Exception {
		mRepository.getData(false).subscribe();
		mRepository.getData(false).subscribe();
		mRepository.getData(false).subscribe();
		mRepository.getData(false).subscribe();
		verify(mCache, Mockito.times(1)).getDataList();
	}

	@Test
	public
	void getDataWithInternet() throws Exception {
		mRepository.getData(true).subscribe();
		verify(mLentaClient, only()).getLists();
		verify(mCache, only()).putDataList(any());
		mRepository.getData(true).subscribe();
		verify(mCache, Mockito.times(2)).putDataList(any());
	}

	@Test
	public
	void getDataWithInternetSubscribe() throws Exception {
		final SimpleCounter simpleCounter = mock(SimpleCounter.class);
		when(mLentaClient.getLists())
				.thenReturn(Observable.create(
						e -> {
							simpleCounter.plusOne();
							e.onNext(new ArrayList<>());
							e.onComplete();
						}
				));
		mRepository.getData(true).subscribe(
				lists -> {
				},
				throwable -> {
				}
		);

		verify(mLentaClient, only()).getLists();
		verify(mCache, only()).putDataList(any());
		verify(simpleCounter, Mockito.times(1)).plusOne();
	}

	@Test
	public
	void getDataWithoutInternetSubscribe() throws Exception {
		final SimpleCounter simpleCounter = mock(SimpleCounter.class);
		when(mCache.getDataList())
				.thenReturn(Observable.create(
						e -> {
							simpleCounter.plusOne();
							e.onNext(new ArrayList<>());
							e.onComplete();
						}
				));
		mRepository.getData(false).subscribe(
				lists -> {
				},
				throwable -> {
				}
		);
		verify(mCache, only()).getDataList();
		verify(simpleCounter, Mockito.times(1)).plusOne();
	}

	public
	class SimpleCounter
	{
		public
		void plusOne() {
		}
	}

	@After
	public
	void tearDown() throws Exception {

	}
}