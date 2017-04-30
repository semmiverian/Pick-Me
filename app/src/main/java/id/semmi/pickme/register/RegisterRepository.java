package id.semmi.pickme.register;

import com.google.android.gms.tasks.OnCompleteListener;

/**
 * Created by Semmiverian on 4/15/17.
 */

public interface RegisterRepository {
    void createUser(String email, String password, OnCompleteListener listener);
    RegisterRepository updateUserName(String name);
}
