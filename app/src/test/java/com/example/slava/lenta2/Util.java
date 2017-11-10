package com.example.slava.lenta2;

import com.example.slava.lenta2.model.data_client.DataDTO;
import com.example.slava.lenta2.view.Data;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import java.util.Arrays;
import java.util.List;

/**
 * Created by vva on 10/11/2017.
 */

public
class Util
{


	public static
	List<DataDTO> getListDataDTO(final DataDTO ... dataDTOS){
		return Arrays.asList(dataDTOS);
	}

	public static
	List<Data> getListData(final Data ... data){
		return Arrays.asList(data);
	}

	public static DataDTO getDataDTO(){
		final Lorem lorem = LoremIpsum.getInstance();
		return new DataDTO(lorem.getUrl(), lorem.getTitle(4), lorem.getParagraphs(1, 1), lorem.getCity(), lorem.getUrl(), lorem.getCity());
	}

	public static Data getData(){
		final Lorem lorem = LoremIpsum.getInstance();
		return new Data(lorem.getUrl(), lorem.getTitle(4), lorem.getParagraphs(1, 1), lorem.getCity(), lorem.getUrl(), lorem.getCity());
	}

}
