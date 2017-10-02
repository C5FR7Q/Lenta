package com.example.slava.lenta2;

import android.os.Bundle;

import com.example.slava.lenta2.model.data_client.LentaClient;
import com.example.slava.lenta2.model.titles_client.TitlesClient;
import com.example.slava.lenta2.presenter.DetailsFragmentPresenter;
import com.example.slava.lenta2.presenter.MainActivityPresenter;
import com.example.slava.lenta2.view.fragment.DetailsFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import io.reactivex.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class DetailsFragmentPresenterTest {
    @Mock private MainActivityPresenter mockMainPresenter;
    @Mock private DetailsFragment mockDetailsView;
    @Mock private LentaClient mockLentaClient;

    private DetailsFragmentPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new DetailsFragmentPresenter(mockDetailsView,
                mockMainPresenter,
                new TitlesClient().getTitles().get(0),
                new TitlesClient(),
                mockLentaClient,
                new TestSchedulersProvider());
    }

//    When view is created and calls onCreateView(...) of presenter, then presenter make view showData.
    @Test
    public void testOnCreateView() {
        when(mockLentaClient.get(anyInt() % 3))
                .thenReturn(Observable.just(Collections.EMPTY_LIST));
        presenter.onCreateView(mock(Bundle.class), mockDetailsView);
        verify(mockMainPresenter).showProgressDialog();
        verify(mockLentaClient, only()).get(anyInt() % 3);
        verify(mockDetailsView, only()).showDatas(any(), any());
        verify(mockMainPresenter).hideProgressDialog();
    }

//    Go next activity in case of right link.
    @Test
    public void testOnRightSelect() {
        String link = "https://lenta.ru/";
        presenter.onSelect(link);
        verify(mockDetailsView, only()).browse(link);
    }

//    Do nothing if link is wrong.
    @Test
    public void testOnWrongSelect() {
        String link = "https://lena.ru";
        presenter.onSelect(link);
        verify(mockDetailsView, never()).browse(link);
    }
}

