package com.example.slava.lenta2.repository;

import com.example.slava.lenta2.view.Data;

import java.util.List;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by vva on 09/11/2017.
 */

public
interface IRepository
{
	PublishSubject<List<Data>> getAllData();
}
