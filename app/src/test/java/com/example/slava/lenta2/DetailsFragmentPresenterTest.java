package com.example.slava.lenta2;

import com.example.slava.lenta2.model.data_client.LentaClient;
import com.example.slava.lenta2.model.titles_client.TitlesClient;
import com.example.slava.lenta2.presenter.DetailsFragmentPresenter;
import com.example.slava.lenta2.presenter.MainPresenter;
import com.example.slava.lenta2.view.fragment.DetailsFragment;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

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
    private DetailsFragmentPresenter presenter;
    private MainPresenter mockMainPresenter;
    private DetailsFragment mockDetailsView;

    @Before
    public void setUp() throws Exception{
        mockMainPresenter = mock(MainPresenter.class);
        mockDetailsView = mock(DetailsFragment.class);
        presenter = new DetailsFragmentPresenter(mockDetailsView,
                mockMainPresenter,
                new TitlesClient().getTitles().get(0),
                new TitlesClient(),
                LentaClient.getInstance());
    }

    @Test
    public void testOnCreateView(){
        presenter.onCreateView();
        verify(mockMainPresenter, only()).showProgressDialog();
    }

    @Test
    public void testOnDestroy(){
        presenter.getDisposables().add(io.reactivex.Observable.just(1).subscribe());
        Assert.assertEquals(presenter.getDisposables().size(), 1);
        presenter.onDestroy();
        Assert.assertEquals(presenter.getDisposables().size(), 0);
    }

    @Test
    public void testOnRightSelect(){
        String link = "https://lenta.ru/";
        presenter.onSelect(link);
        verify(mockDetailsView, only()).browse(link);
    }

    @Test
    public void testOnWrongSelect(){
        String link = "https://lena.ru";
        presenter.onSelect(link);
        verify(mockDetailsView, never()).browse(link);
    }

}