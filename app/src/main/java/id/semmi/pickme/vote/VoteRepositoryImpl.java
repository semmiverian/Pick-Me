package id.semmi.pickme.vote;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import id.semmi.pickme.firebase.Firebase;
import id.semmi.pickme.model.User;

/**
 * Created by Semmiverian on 4/30/17.
 */

public class VoteRepositoryImpl implements VoteRepository {
    private Firebase firebase;
    private Votes votes;
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

}
