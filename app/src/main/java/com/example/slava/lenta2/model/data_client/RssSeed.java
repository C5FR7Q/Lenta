package com.example.slava.lenta2.model.data_client;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by slava on 29.08.2017.
 */

@Root(name = "rss", strict = false)
public
class RssSeed
{

	@ElementList(name = "item", inline = true)
	@Path(value = "channel")
	private List<DataDTO> data;

	public
	List<DataDTO> getData() {
		return data;
	}

	public
	void setData(final List<DataDTO> data) {
		this.data = data;
	}
}

