package id.semmi.pickme.vote.vote_detail;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Semmiverian on 4/30/17.
 */
public class DetailVoteUnitTest {
    private DetailVoteView mockDetailVoteView;
    private DetailVotePresenter detailVotePresenter;


    @Before
    public void setUp() throws Exception {
        mockDetailVoteView = mock(DetailVoteView.class);

        detailVotePresenter = new DetailVotePresenterImpl();
        detailVotePresenter.setView(mockDetailVoteView);
    }

    @Test
    public void shouldReturnNullIfThereIsNoTeamKey () {
        when(detailVotePresenter.setTeamKey(anyString())).thenReturn(null);

    }

}