package id.semmi.pickme.vote.add_vote;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

import id.semmi.pickme.vote.VoteRepository;
import id.semmi.pickme.vote.Votes;

public class AddVotePresenterImpl implements AddVotePresenter {

    private AddVoteView addVoteView;
    private VoteRepository voteRepository;

    public AddVotePresenterImpl(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void setView(AddVoteView addVoteView) {
        this.addVoteView = addVoteView;
    }

    @Override
    public void createVote(List<Vote> mVotes, String chosenDate) {
        String team = "-Kiwf9icbQ5eETSYcwrB";
        Votes votes = new Votes(addVoteView.getVoteName(), addVoteView.getVoteDescription(), mVotes, chosenDate);
        voteRepository.createNewVoteOnTeam(team, votes, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (!task.isSuccessful()) {
                    addVoteView.onError(task.getException().getMessage());
                    return;
                }
                addVoteView.onSuccess("Successfully Creating a new Vote on your team");
            }
        });
    }

}
