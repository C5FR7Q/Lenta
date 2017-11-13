package com.example.slava.lenta2.model.cache;

import com.example.slava.lenta2.Util;
import com.example.slava.lenta2.view.Data;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by vva on 10/11/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public
class CacheTest
{
	@Rule
	public TemporaryFolder mFolder = new TemporaryFolder();

	private Cache mCache;
	private List<List<Data>> mLists;

	@Before
	public
	void setUp() throws Exception {
		final File root = mFolder.getRoot();
		final File testFile = new File(root.getPath() + File.separator + "test");
		mLists = new ArrayList<>();
		mLists.add(Util.getListData(Util.getData()));

		Assert.assertFalse(testFile.exists());

		//noinspection ResultOfMethodCallIgnored
		testFile.mkdir();

		mCache = new Cache(testFile);

		Assert.assertTrue(root.exists());
		Assert.assertTrue(root.isDirectory());

		Assert.assertTrue(testFile.exists());
		Assert.assertTrue(testFile.isDirectory());
	}


	@Test
	public
	void getDataList() throws Exception {
		mCache.putDataList(mLists);
		final Observable<List<List<Data>>> dataList = mCache.getDataList();
		dataList.subscribe(lists -> Assert.assertTrue(mLists.equals(lists)));
	}

	@Test
	public
	void putDataList() throws Exception {
		Assert.assertFalse(mCache.putDataList(mLists) == null);
//		if NonNull then task is done well.
	}

	@After
	public
	void tearDown() throws Exception {

	}

}