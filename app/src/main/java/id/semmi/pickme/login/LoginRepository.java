package id.semmi.pickme.login;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import id.semmi.pickme.model.User;

/**
 * Created by Semmiverian on 4/15/17.
 */

public class LoginRepository {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    public LoginRepository(FirebaseAuth firebaseAuth, DatabaseReference databaseReference) {
        this.firebaseAuth = firebaseAuth;
        this.databaseReference = databaseReference;
    }

    public void save () {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child("users/" + user.getUid())
                .setValue(new User(user.getDisplayName(), user.getUid(), user.getEmail()));
    }
}
