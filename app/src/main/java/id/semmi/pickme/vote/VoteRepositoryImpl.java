package id.semmi.pickme.vote;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DatabaseReference;

import id.semmi.pickme.firebase.Firebase;

/**
 * Created by Semmiverian on 4/30/17.
 */

public class VoteRepositoryImpl implements VoteRepository {
    private Firebase firebase;

    public VoteRepositoryImpl(Firebase firebase) {
        this.firebase = firebase;
    }


    @Override
    public void createVoteOnTeam(String teamKey, Votes votes, OnCompleteListener listener) {
        DatabaseReference databaseReference = firebase.getDatabaseReference().child("/teams/" + teamKey + "/votes/");

        String key = databaseReference.push().getKey();

        databaseReference.child(key)
                .setValue(votes)
                .addOnCompleteListener(listener);
    }
}
