package id.semmi.pickme.vote.add_vote;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import id.semmi.pickme.vote.VoteRepository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Semmiverian on 4/29/17.
 */
public class AddVotePresenterUnitTest {
    private VoteRepository mockVoteRepository;
    private AddVoteView mockVoteView;
    private AddVotePresenter presenter;


    @Before
    public void setUp() throws Exception {
        mockVoteRepository = mock(VoteRepository.class);
        mockVoteView = mock(AddVoteView.class);

        presenter = new AddVotePresenterImpl(mockVoteRepository);
        presenter.setView(mockVoteView);
    }
    
}