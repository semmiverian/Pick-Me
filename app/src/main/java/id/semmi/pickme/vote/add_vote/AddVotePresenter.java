package id.semmi.pickme.vote.add_vote;

import java.util.List;

import id.semmi.pickme.BasicPresenter;

/**
 * Created by Semmiverian on 4/29/17.
 */

public interface AddVotePresenter extends BasicPresenter {
    void setView (AddVoteView addVoteView);
    void createVote(List<Vote> mVotes, String chosenDate);
}
