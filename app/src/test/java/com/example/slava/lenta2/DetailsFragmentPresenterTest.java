package com.example.slava.lenta2;

import android.content.Context;

import com.example.slava.lenta2.presenter.DetailsFragmentPresenter;
import com.example.slava.lenta2.presenter.MainPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class DetailsFragmentPresenterTest {
    DetailsFragmentPresenter presenter;
    private MainPresenter mainPresenter;

    @Before
    public void setUp() throws Exception{
//        presenter = new DetailsFragmentPresenter(mock(DetailsFragment.class),
//                mainPresenter,
//                new TitlesClient().getTitles().get(0),
//                new TitlesClient(),
//                LentaClient.getInstance());
        presenter = mock(DetailsFragmentPresenter.class);
    }

    @Test
    public void testOnCreateView(){
        presenter.onCreateView();
        verify(mainPresenter, only()).showProgressDialog();
        verify(presenter.getDisposables(), only()).add(any());
    }

    @Test
    public void testOnDestroy(){
        presenter.onDestroy();
        verify(presenter.getDisposables(), only()).dispose();
    }

    @Test
    public void testOnRightSelect(){
        String link = "https://lenta.ru/";
        Context context = mock(Context.class);
        presenter.onSelect(link, context);
        verify(presenter, only()).browse(link, context);
    }

    @Test
    public void testOnWrongSelect(){
        String link = "https://lena.ru";
        Context context = mock(Context.class);
        presenter.onSelect(link, context);
        verify(presenter, never()).browse(link, context);
    }

}