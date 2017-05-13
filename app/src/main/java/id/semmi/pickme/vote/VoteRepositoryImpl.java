package id.semmi.pickme.vote;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import id.semmi.pickme.firebase.Firebase;
import id.semmi.pickme.model.User;
import id.semmi.pickme.vote.add_vote.Vote;

public class VoteRepositoryImpl implements VoteRepository {
    private Firebase firebase;
    private Votes votes;
    private long allUserCount;

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
        firebase.getDatabaseReference().child("/teams/" + teamKey + "/votes/" + voteKey + "/allUsers/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allUserCount = dataSnapshot.getChildrenCount();
                firebase.getDatabaseReference().child("/teams/" + teamKey + "/votes/" + voteKey + "/votes/" + position + "/users/").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        long count = dataSnapshot.getChildrenCount();
                        long finalPercentage = (count != 0) ? Math.round(count / allUserCount * 100) : 100;
                        updateDatabaseChildren(vote, teamKey, voteKey, position, finalPercentage, listener);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateDatabaseChildren(Vote vote, String teamKey, String voteKey, int position, long percentage, OnCompleteListener listener) {
        FirebaseUser firebaseUser = firebase.getFirebaseAuth().getCurrentUser();
        HashMap<String, Object> map = new HashMap<>();

        map.put("/teams/" + teamKey + "/votes/" + voteKey + "/pendingVoteUsers/" + firebaseUser.getUid(), null);
        map.put("/teams/" + teamKey + "/votes/" + voteKey + "/alreadyVoteUsers/" + firebaseUser.getUid(), firebaseUser);
        map.put("/teams/" + teamKey + "/votes/" + voteKey + "/votes/" + position + "/percentage", percentage);
        map.put("/teams/" + teamKey + "/votes/" + voteKey + "/votes/" + position + "/users/" + firebaseUser.getUid(), firebaseUser);

        firebase.getDatabaseReference().updateChildren(map).addOnCompleteListener(listener);
    }
}
