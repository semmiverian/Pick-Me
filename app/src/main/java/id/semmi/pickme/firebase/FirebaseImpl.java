package id.semmi.pickme.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Semmiverian on 4/29/17.
 */

public class FirebaseImpl implements Firebase {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    public FirebaseImpl(FirebaseAuth firebaseAuth, DatabaseReference databaseReference) {
        this.firebaseAuth = firebaseAuth;
        this.databaseReference = databaseReference;
    }

    @Override
    public FirebaseAuth getFirebaseAuth() {
        return this.firebaseAuth;
    }

    @Override
    public DatabaseReference getDatabaseReference() {
        return this.databaseReference;
    }
}
