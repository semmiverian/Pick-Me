package id.semmi.pickme.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import id.semmi.pickme.DialogHelper;
import id.semmi.pickme.R;
import id.semmi.pickme.dagger.PickMeApplication;
import id.semmi.pickme.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, LoginView {
    private static final int RC_SIGN_IN = 13112;
    private static final String TAG = "DEBUG";

    @Inject GoogleSignInOptions googleSignInOptions;
    @Inject LoginPresenter mLoginPresenter;
    @Inject FirebaseAuth mFirebaseAuth;

    @BindView(R.id.sign_in_button) SignInButton mSignInButton;
    @BindView(R.id.emailInput) AppCompatEditText emailInput;
    @BindView(R.id.passwordInput) AppCompatEditText passwordInput;

    private GoogleApiClient mGoogleApiClient;
    private Unbinder unbinder;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DialogHelper dialogHelper;
    private MaterialDialog materialDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PickMeApplication) getApplication()).getApplicationComponent().inject(this);
        setContentView(R.layout.activity_login);
        // TEMPORARY
        FirebaseAuth.getInstance().signOut();
        unbinder = ButterKnife.bind(this);
        dialogHelper = new DialogHelper(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mLoginPresenter.handleAuthStateChange(firebaseAuth.getCurrentUser());
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoginPresenter.setView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mAuthListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @OnClick(R.id.sign_in_button)
    public void onSignIn (View v) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @OnClick(R.id.loginButton)
    public void onLoginClick (View v) {
        materialDialog = dialogHelper.loadingDialog("Processing", "Authenticating your data");

        mLoginPresenter.handleUserLogIn();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            mLoginPresenter.handleLoggedInResult(result, mFirebaseAuth, this);
        }
    }


    @Override
    public void showErrorMessage(String message) {
        // Something went wrong or user signed out
        if ( materialDialog != null && materialDialog.isShowing()) {
            materialDialog.dismiss();
            dialogHelper.singlePositiveDialog("Error", message, "Ok", new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    dialog.dismiss();
                }
            });
        }

        Log.d(TAG, "showErrorMessage: something error?");
        Toast.makeText(this, "Have not Logged in yet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessLoggedIn(String loggedInName) {
        // go to the next activity
        if (materialDialog.isShowing()) {
            materialDialog.dismiss();
        }

        Log.d(TAG, "onSuccessLoggedIn: " + loggedInName);
        Toast.makeText(this, "Logged In as " + loggedInName, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAlreadyLoggedInMessage(String loggedInName) {
        Toast.makeText(this, "Already Logged in ", Toast.LENGTH_SHORT).show();

    }

    @Override
    public String getEmail() {
        return emailInput.getText().toString();
    }

    @Override
    public String getPassword() {
        return passwordInput.getText().toString();
    }

    @OnClick(R.id.registerInfo)
    public void onRegisterInfoClick (View v) {
        Intent goToRegisterIntent = new Intent(this, RegisterActivity.class);
        startActivity(goToRegisterIntent);
    }
}
