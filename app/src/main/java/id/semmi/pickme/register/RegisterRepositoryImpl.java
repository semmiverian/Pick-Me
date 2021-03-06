package id.semmi.pickme.register;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;

import id.semmi.pickme.model.User;

/**
 * Created by Semmiverian on 4/30/17.
 */

public class RegisterRepositoryImpl implements RegisterRepository {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    public RegisterRepositoryImpl(FirebaseAuth firebaseAuth, DatabaseReference databaseReference) {
        this.firebaseAuth = firebaseAuth;
        this.databaseReference = databaseReference;

    }

    @Override
    public void createUser (String email, String password, OnCompleteListener listener ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener);
    }

    @Override
    public RegisterRepository updateUserName(String name) {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        save();
                    }
                }
            });
        }

        return this;
    }



    private void save () {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child("users/" + user.getUid())
                .setValue(new User(user.getDisplayName(), user.getUid(), user.getEmail()));
    }
}
