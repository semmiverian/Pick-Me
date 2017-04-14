package id.semmi.pickme.register;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

import id.semmi.pickme.BasicPresenter;

/**
 * Created by Semmiverian on 4/14/17.
 */

public interface RegisterPresenter extends BasicPresenter{
    void register(Activity activity);
    void setView(RegisterView view);
}
