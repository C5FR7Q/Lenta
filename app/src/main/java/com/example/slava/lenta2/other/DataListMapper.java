package com.example.slava.lenta2.other;

import com.example.slava.lenta2.model.data_client.DataDTO;
import com.example.slava.lenta2.view.Data;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by slava on 13.10.2017.
 */

public
class DataListMapper
		implements Function<List<DataDTO>, List<Data>>
{
	@Override
	public
	List<Data> apply(@NonNull final List<DataDTO> dataDTOs) throws Exception {
		final List<Data> dataList = new ArrayList<>();
		for (DataDTO dataDTO : dataDTOs) {
			try {
				dataList.add(new Data(dataDTO.getLink(),
						dataDTO.getTitle(), dataDTO.getDescription(),
						dataDTO.getPubDate().substring(0, dataDTO.getPubDate().length() - 4),
						dataDTO.getPicLink(), dataDTO.getCategory()
				));
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		}
		return dataList;
	}
}
