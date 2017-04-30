package id.semmi.pickme.login;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import id.semmi.pickme.model.User;

/**
 * Created by Semmiverian on 4/15/17.
 */

public interface LoginRepository {
    void save();
    void authenticated(String email, String password, OnCompleteListener listener);
}
