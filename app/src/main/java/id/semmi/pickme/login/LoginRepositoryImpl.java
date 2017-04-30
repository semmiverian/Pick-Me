package id.semmi.pickme.login;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import id.semmi.pickme.model.User;

/**
 * Created by Semmiverian on 4/30/17.
 */

public class LoginRepositoryImpl implements LoginRepository {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    public LoginRepositoryImpl(FirebaseAuth firebaseAuth, DatabaseReference databaseReference) {
        this.firebaseAuth = firebaseAuth;
        this.databaseReference = databaseReference;
    }

    @Override
    public void save() {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child("users/" + user.getUid())
                .setValue(new User(user.getDisplayName(), user.getUid(), user.getEmail()));
    }

    @Override
    public void authenticated(String email, String password, OnCompleteListener listener) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener);
    }
}
