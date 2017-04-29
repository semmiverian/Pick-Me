package id.semmi.pickme.vote;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DatabaseReference;

import id.semmi.pickme.firebase.Firebase;

/**
 * Created by Semmiverian on 4/29/17.
 */

public class VoteRepository {

    private Firebase firebase;

    public VoteRepository(Firebase firebase) {
        this.firebase = firebase;
    }

    public void createNewVoteOnTeam(String teamKey, Votes votes, OnCompleteListener listener) {
        DatabaseReference databaseReference = firebase.getDatabaseReference().child("/teams/" + teamKey + "/votes/");

        String key = databaseReference.push().getKey();

        databaseReference.child(key)
                .setValue(votes)
                .addOnCompleteListener(listener);
    }
}
