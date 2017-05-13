package id.semmi.pickme.vote;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.ValueEventListener;

import id.semmi.pickme.vote.add_vote.Vote;

/**
 * Created by Semmiverian on 4/29/17.
 */

public interface VoteRepository {
    void createNewVoteOnTeam(String teamKey, Votes votes, OnCompleteListener listener);
    void fetchVote(String teamKey, String voteKey, ValueEventListener listener);
    void setUserVote(Vote vote, String teamKey, String voteKey, int position, OnCompleteListener<Void> listener);
    void allowedToVote(String teamKey, String votesKey, ValueEventListener listener);
}