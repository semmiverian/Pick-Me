package id.semmi.pickme.vote.vote_detail;

import java.util.List;

import id.semmi.pickme.BasicView;
import id.semmi.pickme.vote.Votes;

/**
 * Created by Semmiverian on 4/30/17.
 */

public interface DetailVoteView extends BasicView {
    void fetchVoteDetail (Votes votes);
    boolean allowedToVotes (boolean status);
}
