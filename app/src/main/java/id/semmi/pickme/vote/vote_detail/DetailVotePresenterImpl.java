package id.semmi.pickme.vote.vote_detail;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import id.semmi.pickme.vote.VoteRepository;
import id.semmi.pickme.vote.Votes;

/**
 * Created by Semmiverian on 4/30/17.
 */

public class DetailVotePresenterImpl implements DetailVotePresenter {
    private DetailVoteView detailVoteView;
    private VoteRepository voteRepository;
    private String teamKey = "";
    private String votesKey = "";

    public DetailVotePresenterImpl(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Override
    public void resume() {
        this.fetchVoteOptions();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void setView(DetailVoteView detailVoteView) {
        this.detailVoteView = detailVoteView;
    }

    @Override
    public void fetchVoteOptions() {
        teamKey = "-Kiwf9icbQ5eETSYcwrB";
        votesKey = "-KiwjptZZL3My_fCyWui";
        if (!teamKey.equals("") && !votesKey.equals("")) {
            voteRepository.fetchVote(this.teamKey, this.votesKey, new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    detailVoteView.fetchVoteDetail(dataSnapshot.getValue(Votes.class));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    detailVoteView.onError(databaseError.getMessage());
                }
            });
        }
    }

}
