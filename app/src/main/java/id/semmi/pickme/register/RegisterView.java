package id.semmi.pickme.register;

/**
 * Created by Semmiverian on 4/14/17.
 */

public interface RegisterView {
    String getEmailAdress();
    String getPassword();
    void showErrorMessage();
    void onSuccessLoggedIn();
}
