package id.semmi.pickme.register;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Semmiverian on 4/14/17.
 */

public class RegisterPresenterImpl implements RegisterPresenter {
    private RegisterView mView;
    private FirebaseAuth mFirebaseAuth;
    private RegisterRepository registerRepository;

    public RegisterPresenterImpl(FirebaseAuth firebaseAuth, RegisterRepository registerRepository) {
        this.mFirebaseAuth = firebaseAuth;
        this.registerRepository = registerRepository;
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void register(Activity activity) {
        if (TextUtils.isEmpty(mView.getEmailAdress()) || TextUtils.isEmpty(mView.getPassword()) || TextUtils.isEmpty(mView.getName()))  {
            mView.showErrorMessage("Please Fill All Field");
            return;
        }

        registerRepository.createUser(mView.getEmailAdress(), mView.getPassword(), new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (!task.isSuccessful()) {
                    mView.showErrorMessage(task.getException().getMessage());
                    return;
                }
                mView.onSuccessLoggedIn();
                registerRepository.updateUserName(mView.getName());
            }
        });

    }

    @Override
    public void setView(RegisterView view) {
        mView = view;
    }
}
