package com.example.slava.lenta2.model.cache;

import com.example.slava.lenta2.view.Data;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by slava on 13.10.2017.
 */

final public
class Serializer
{
	private final Gson gson = new Gson();

	public
	String serialize(final Object object) {
		return gson.toJson(object);
	}

	public
	List<List<Data>> deserialize(final String string) {
		return gson.fromJson(string, new TypeToken<List<List<Data>>>() {}.getType());
	}
}
