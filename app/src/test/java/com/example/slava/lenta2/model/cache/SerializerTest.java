package com.example.slava.lenta2.model.cache;

import com.example.slava.lenta2.Util;
import com.example.slava.lenta2.view.Data;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vva on 13/11/2017.
 */
public
class SerializerTest
{
	@Test
	public
	void test() throws Exception {
		final List<List<Data>> lists = new ArrayList<>();
		lists.add(Util.getListData(Util.getData()));
		final Serializer serializer = new Serializer();
		final String serializedJson = serializer.serialize(lists);
		final List<List<Data>> deserializeLists = serializer.deserialize(serializedJson);
		Assert.assertTrue(lists.equals(deserializeLists));
	}
}