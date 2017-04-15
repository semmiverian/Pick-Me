package id.semmi.pickme.login;


/**
 * Created by Semmiverian on 4/14/17.
 */

public interface LoginView {
    void showErrorMessage(String message);
    void onSuccessLoggedIn(String loggedInName);
    void showAlreadyLoggedInMessage(String loggedInName);
    String getEmail();
    String getPassword();
}
