package id.semmi.pickme.vote.vote_detail;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import id.semmi.pickme.vote.VoteRepository;
import id.semmi.pickme.vote.Votes;
import id.semmi.pickme.vote.add_vote.Vote;

/**
 * Created by Semmiverian on 4/30/17.
 */

public class DetailVotePresenterImpl implements DetailVotePresenter {
    private DetailVoteView detailVoteView;
    private VoteRepository voteRepository;

    public DetailVotePresenterImpl(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Override
    public void resume() {
        this.fetchVoteOptions();
        this.isAllowedToVote();
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
        String teamKey = "-Klieo8qDmDra4rev12V";
        String votesKey = "-KliyCrJ1OALPubQ6eDE";
        if (!teamKey.equals("") && !votesKey.equals("")) {
            voteRepository.fetchVote(teamKey, votesKey, new ValueEventListener() {
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

    @Override
    public void setUserVote(final Vote vote, int position) {
        String teamKey = "-Klieo8qDmDra4rev12V";
        String votesKey = "-KliyCrJ1OALPubQ6eDE";
        voteRepository.setUserVote(vote, teamKey, votesKey, position, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("tes", "onComplete: " + task.isComplete());
                if (!task.isSuccessful()) {
                    detailVoteView.onError(task.getException().getMessage());
                    return;
                }
                detailVoteView.onSuccess("Successfully vote on " + vote.getText());
            }
        });
    }

    @Override
    public void isAllowedToVote() {
        String teamKey = "-Klieo8qDmDra4rev12V";
        String votesKey = "-KliyCrJ1OALPubQ6eDE";
        voteRepository.allowedToVote(teamKey, votesKey, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                detailVoteView.allowedToVotes(!dataSnapshot.exists());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
