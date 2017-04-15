package id.semmi.pickme.login;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
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

import org.w3c.dom.Text;

import id.semmi.pickme.dagger.PickMeApplication;

/**
 * Created by Semmiverian on 4/14/17.
 */

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView loginView;
    private LoginRepository loginRepository;


    public LoginPresenterImpl(LoginRepository loginRepository) {

        this.loginRepository = loginRepository;
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
                                loginRepository.save();

                                if (!task.isSuccessful()) {
                                    loginView.showErrorMessage(task.getException().getMessage());
                                }
                            }
                        });
        } else {
            this.loginView.showErrorMessage(googleSignInResult.getStatus().toString());
        }
    }


    @Override
    public void handleAuthStateChange(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            loginView.onSuccessLoggedIn(firebaseUser.getDisplayName());
        } else {
            this.loginView.showErrorMessage("Sign Out");
        }
    }

    @Override
    public void handleUserLogIn() {
        if (TextUtils.isEmpty(loginView.getEmail()) || TextUtils.isEmpty(loginView.getPassword())) {
            loginView.showErrorMessage("Please Fill all the field");
            return;
        }

        loginRepository.authenticated(loginView.getEmail(), loginView.getPassword(), new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (!task.isSuccessful()) {
                    loginView.showErrorMessage(task.getException().getMessage());
                    return;
                }
                loginView.onSuccessLoggedIn(loginView.getEmail());
            }
        });
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }
}
