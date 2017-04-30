package id.semmi.pickme.vote;

import com.google.android.gms.tasks.OnCompleteListener;

/**
 * Created by Semmiverian on 4/29/17.
 */

public interface VoteRepository {
    void createVoteOnTeam(String teamKey, Votes votes, OnCompleteListener listener);

}