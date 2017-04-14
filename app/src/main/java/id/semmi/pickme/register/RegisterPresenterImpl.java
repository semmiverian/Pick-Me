package id.semmi.pickme.register;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Semmiverian on 4/14/17.
 */

public class RegisterPresenterImpl implements RegisterPresenter {
    private RegisterView mView;

    private FirebaseAuth mFirebaseAuth;

    public RegisterPresenterImpl(FirebaseAuth firebaseAuth) {
        this.mFirebaseAuth = firebaseAuth;
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void register(Activity activity) {
        if (TextUtils.isEmpty(mView.getEmailAdress()) || TextUtils.isEmpty(mView.getPassword()))  {
            mView.showErrorMessage();
            return;
        }

        Log.d("aaa", "register: " + mView.getEmailAdress());
        Log.d("aaa", "register: " + mView.getPassword());

        mFirebaseAuth.createUserWithEmailAndPassword(mView.getEmailAdress(), mView.getPassword())
             .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     mView.onSuccessLoggedIn();
                     if (!task.isSuccessful()) {
                         Log.d("aaa", "onComplete: " + task.getException());
                         mView.showErrorMessage();
                     }
                 }
             });

    }

    @Override
    public void setView(RegisterView view) {
        mView = view;
    }
}
