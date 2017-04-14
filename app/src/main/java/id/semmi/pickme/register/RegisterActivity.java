package id.semmi.pickme.register;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import id.semmi.pickme.R;
import id.semmi.pickme.dagger.PickMeApplication;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    @BindView(R.id.emailInput) AppCompatEditText emailInput;
    @BindView(R.id.passwordInput) AppCompatEditText passwordInput;
    @BindView(R.id.registerButton) AppCompatButton registerButton;

    @Inject RegisterPresenter mRegisterPresenter;

    private Unbinder unbinder;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ((PickMeApplication) getApplication()).getApplicationComponent().inject(this);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRegisterPresenter.setView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.registerButton)
    public void onRegisterClick (View v) {
        mRegisterPresenter.register(this);
    }

    @Override
    public String getEmailAdress() {
        return emailInput.getText().toString();
    }

    @Override
    public String getPassword() {
        return passwordInput.getText().toString();
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessLoggedIn() {
        Toast.makeText(this, "Berhasil Bro", Toast.LENGTH_SHORT).show();
    }
}
