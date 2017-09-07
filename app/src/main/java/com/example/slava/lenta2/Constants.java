package com.example.slava.lenta2;

import java.util.ArrayList;

/**
 * Created by slava on 07.09.2017.
 */

public class Constants {
    public static final String MAIN_PRESENTER = "main_presenter";
    public static final String TITLE = "title";
    public static ArrayList<String> getTitles(){
        ArrayList<String> titles = new ArrayList<>();
        titles.add("Hottest news");
        titles.add("Latest news");
        titles.add("All news");
        return titles;
    }
}
