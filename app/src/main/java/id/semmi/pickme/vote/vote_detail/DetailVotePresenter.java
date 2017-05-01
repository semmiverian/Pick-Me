package id.semmi.pickme.vote.vote_detail;

import java.util.List;

import id.semmi.pickme.BasicPresenter;
import id.semmi.pickme.vote.Votes;

/**
 * Created by Semmiverian on 4/30/17.
 */

public interface DetailVotePresenter extends BasicPresenter{
    void setView(DetailVoteView detailVoteView);
    void fetchVoteOptions();
}
