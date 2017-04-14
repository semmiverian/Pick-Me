package id.semmi.pickme.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import id.semmi.pickme.R;
import id.semmi.pickme.dagger.PickMeApplication;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, LoginView {
    private static final int RC_SIGN_IN = 13112;
    private static final String TAG = "DEBUG";

    @Inject
    GoogleSignInOptions googleSignInOptions;
    @Inject
    LoginPresenter mLoginPresenter;

    @BindView(R.id.sign_in_button)
    SignInButton mSignInButton;
    @BindView(R.id.sign_email)
    AppCompatTextView mSignIn;

    private GoogleApiClient mGoogleApiClient;
    private Unbinder unbinder;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PickMeApplication) getApplication()).getApplicationComponent().inject(this);
        setContentView(R.layout.activity_login);
        unbinder = ButterKnife.bind(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
        mFirebaseAuth = FirebaseAuth.getInstance();
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
    public void onSignIn(View v) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @OnClick(R.id.sign_email)
    public void onSignInEmail(View v) {
        // Go To the next method
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
    public void showErrorMessage() {
        // Something went wrong or user signed out
        Log.d(TAG, "showErrorMessage: something error?");
        Toast.makeText(this, "Have not Logged in yet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessLoggedIn(String loggedInName) {
        // go to the next activity
        Log.d(TAG, "onSuccessLoggedIn: " + loggedInName);
        Toast.makeText(this, "Logged In as " + loggedInName, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAlreadyLoggedInMessage(String loggedInName) {
        Toast.makeText(this, "Already Logged in ", Toast.LENGTH_SHORT).show();

    }
}
