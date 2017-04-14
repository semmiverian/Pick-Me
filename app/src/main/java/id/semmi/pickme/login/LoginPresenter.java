package id.semmi.pickme.login;

import android.app.Activity;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import id.semmi.pickme.BasicPresenter;

/**
 * Created by Semmiverian on 4/14/17.
 */

public interface LoginPresenter extends BasicPresenter {
    void setView(LoginView view);
    void handleLoggedInResult (GoogleSignInResult googleSignInResult, FirebaseAuth firebaseAuth, Activity activity);
    void handleAuthStateChange (FirebaseUser firebaseUser);
}
