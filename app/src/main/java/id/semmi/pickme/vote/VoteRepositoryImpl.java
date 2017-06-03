package id.semmi.pickme.vote;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import id.semmi.pickme.firebase.Firebase;
import id.semmi.pickme.model.User;
import id.semmi.pickme.vote.add_vote.Vote;

public class VoteRepositoryImpl implements VoteRepository {
    private Firebase firebase;
    private long alreadyVotedCount;

    public VoteRepositoryImpl(Firebase firebase) {
        this.firebase = firebase;
    }

    @Override
    public void createNewVoteOnTeam(final String teamKey, Votes votes, OnCompleteListener listener) {
        DatabaseReference databaseReference = firebase.getDatabaseReference().child("/teams/" + teamKey + "/votes/");

        final String key = databaseReference.push().getKey();

        databaseReference.child(key)
                .setValue(votes)
                .addOnCompleteListener(listener);

        firebase.getDatabaseReference().child("/teams/" + teamKey + "/user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> userMap = new HashMap<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User user = data.getValue(User.class);
                    userMap.put("/teams/" + teamKey + "/votes/" + key + "/pendingVoteUsers/" + user.getUuid(), user);
                    userMap.put("/teams/" + teamKey + "/votes/" + key + "/allUsers/" + user.getUuid(), user);
                }

                firebase.getDatabaseReference().updateChildren(userMap);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void fetchVote(String teamKey, String voteKey, ValueEventListener listener) {
        firebase.getDatabaseReference().child("/teams/" + teamKey + "/votes/" + voteKey).addListenerForSingleValueEvent(listener);
    }

    @Override
    public void allowedToVote(String teamKey, String votesKey, ValueEventListener listener) {
        DatabaseReference databaseReference = firebase.getDatabaseReference().child("/teams/" + teamKey + "/votes/"+ votesKey + "/alreadyVoteUsers/" + firebase.getFirebaseAuth().getCurrentUser().getUid());

        databaseReference.addListenerForSingleValueEvent(listener);
    }

    @Override
    public void setUserVote(final Vote vote, final String teamKey, final String voteKey, final int position, final OnCompleteListener listener) {

        firebase.getDatabaseReference().child("/teams/" + teamKey + "/votes/" + voteKey + "/alreadyVoteUsers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                alreadyVotedCount = dataSnapshot.getChildrenCount();
                String basePath = "/teams/" + teamKey + "/votes/" + voteKey;
                // plus one to include the user that would like to vote
                updateAllVotesOption(basePath, alreadyVotedCount += 1, listener, vote);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void updateAllVotesOption(final String basePath, final long alreadyVotedCount, final OnCompleteListener listener, final Vote chosenVote) {
        final HashMap<String, Object> map = new HashMap<>();
        firebase.getDatabaseReference().child(basePath + "/votes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser authenticatedUser = firebase.getFirebaseAuth().getCurrentUser();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Vote vote = snapshot.getValue(Vote.class);
                    int votedUserInTheVote;
                    votedUserInTheVote = (vote.getUsers() != null) ? vote.getUsers().size() : 0;

                    if (chosenVote.getText().equals(vote.getText())) {
                        votedUserInTheVote += 1;
                        map.put(basePath + "/votes/" + snapshot.getKey() + "/users/" + authenticatedUser.getUid(), authenticatedUser);
                    }

                    float newPercentage = (float) votedUserInTheVote / (float) alreadyVotedCount * 100;
                    DecimalFormat numberFormat = new DecimalFormat("#.0");
                    Log.d("tes", "onDataChange: " + numberFormat.format(newPercentage));
                    map.put(basePath + "/votes/" + snapshot.getKey() + "/percentage/", numberFormat.format(newPercentage));
                }
                // remove user state from not voted collection to already voted collection
                map.put(basePath + "/pendingVoteUsers/" + authenticatedUser.getUid(), null);
                map.put(basePath + "/alreadyVoteUsers/" + authenticatedUser.getUid(), authenticatedUser);
                firebase.getDatabaseReference().updateChildren(map).addOnCompleteListener(listener);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
