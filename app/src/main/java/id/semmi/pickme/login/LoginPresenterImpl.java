package id.semmi.pickme.login;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import id.semmi.pickme.dagger.PickMeApplication;

/**
 * Created by Semmiverian on 4/14/17.
 */

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView loginView;

    public LoginPresenterImpl() {

    }

    @Override
    public void setView(LoginView view) {
        this.loginView = view;
    }

    @Override
    public void handleLoggedInResult(GoogleSignInResult googleSignInResult, FirebaseAuth firebaseAuth, Activity activity) {
        if (googleSignInResult.isSuccess()) {
            final GoogleSignInAccount acct = googleSignInResult.getSignInAccount();
            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            firebaseAuth.signInWithCredential(credential)
                        .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                loginView.onSuccessLoggedIn(acct.getDisplayName());

                                if (!task.isSuccessful()) {
                                    loginView.showErrorMessage();
                                }
                            }
                        });
        } else {
            this.loginView.showErrorMessage();
        }
    }


    @Override
    public void handleAuthStateChange(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            Log.d("aaa", "handleAuthStateChange: " +firebaseUser.getDisplayName());
            loginView.onSuccessLoggedIn(firebaseUser.getDisplayName());
        } else {
            this.loginView.showErrorMessage();
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }
}
