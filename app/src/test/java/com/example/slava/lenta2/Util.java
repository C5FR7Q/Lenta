package com.example.slava.lenta2;

import com.example.slava.lenta2.model.data_client.DataDTO;
import com.example.slava.lenta2.view.Data;

import java.util.Arrays;
import java.util.List;

/**
 * Created by vva on 10/11/2017.
 */

public
class Util
{
	public static
	List<DataDTO> getListDataDTO(DataDTO ... dataDTOS){
		return Arrays.asList(dataDTOS);
	}

	public static
	List<Data> getListData(Data ... data){
		return Arrays.asList(data);
	}

}
