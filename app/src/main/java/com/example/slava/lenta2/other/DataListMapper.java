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

public class DataListMapper implements Function<List<DataDTO>, List<Data>> {
    @Override
    public List<Data> apply(@NonNull List<DataDTO> dataDTOs) throws Exception {
        List<Data> data = new ArrayList<>();
        dataDTOs.forEach(dataDTO -> data.add(new Data(dataDTO.getLink(),
                dataDTO.getTitle(), dataDTO.getDescription(),
                dataDTO.getPubDate(), dataDTO.getPicLink(), dataDTO.getCategory())));
        return data;
    }
}