package id.semmi.pickme.register;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import id.semmi.pickme.DialogHelper;
import id.semmi.pickme.R;
import id.semmi.pickme.dagger.PickMeApplication;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    @BindView(R.id.emailInput) AppCompatEditText emailInput;
    @BindView(R.id.passwordInput) AppCompatEditText passwordInput;
    @BindView(R.id.registerButton) AppCompatButton registerButton;
    @BindView(R.id.nameInput) AppCompatEditText nameInput;

    @Inject RegisterPresenter mRegisterPresenter;

    private Unbinder unbinder;
    private MaterialDialog materialDialog;
    private DialogHelper mDialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ((PickMeApplication) getApplication()).getApplicationComponent().inject(this);
        mDialogHelper = new DialogHelper(this);
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
        materialDialog =  mDialogHelper.loadingDialog("Procces", "Registering your data");
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
    public String getName() {
        return nameInput.getText().toString();
    }

    @Override
    public void showErrorMessage(String message) {
        if (materialDialog.isShowing()) {
            materialDialog.dismiss();
            mDialogHelper.singlePositiveDialog("Error", message, "Ok", new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    dialog.dismiss();
                }
            });
        }
        Toast.makeText(this, "Something Went Wrong " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessLoggedIn() {
        if (materialDialog.isShowing()) {
            materialDialog.dismiss();
        }

        Toast.makeText(this, "Berhasil Bro", Toast.LENGTH_SHORT).show();
    }
}
