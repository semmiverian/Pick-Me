package id.semmi.pickme.team.addteam;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.pchmn.materialchips.ChipsInput;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import id.semmi.pickme.DialogHelper;
import id.semmi.pickme.MyActivity;
import id.semmi.pickme.R;
import id.semmi.pickme.dagger.PickMeApplication;

public class AddTeamActivity extends AppCompatActivity implements AddTeamView {
    private static final String TAG = AddTeamActivity.class.toString();
    @BindView(R.id.chips_input) ChipsInput chipsInput;
    @BindView(R.id.team_name_input) AppCompatEditText team_name_input;
    @BindView(R.id.description_input) AppCompatEditText description_input;
    @BindView(R.id.create_team_button) AppCompatButton create_team_button;
    @BindDrawable(R.drawable.avatar_1) Drawable drawable;

    @Inject AddTeamPresenter addTeamPresenter;

    private Unbinder unbinder;
    private DialogHelper dialogHelper;
    private MaterialDialog materialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((PickMeApplication) getApplication()).getApplicationComponent().inject(this);
        unbinder = ButterKnife.bind(this);
        dialogHelper = new DialogHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        addTeamPresenter.setView(this);
        addTeamPresenter.fetchUserChip(drawable);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void fetchUsersData(List<UserChip> userChips) {
        if (userChips != null || userChips.size() != 0) {
            chipsInput.setFilterableList(userChips);
        }
    }

    @Override
    public void onSuccessCreatingTeam(String message) {
        if (materialDialog != null && materialDialog.isShowing()) {
            materialDialog.dismiss();
            dialogHelper.singlePositiveDialog("Success", message, "Ok", new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    dialog.dismiss();
                }
            });
        }

    }

    @Override
    public void onFailedCreatingTeam(String message) {
        Log.e(TAG, "onFailedCreatingTeam: " + message );
    }

    @Override
    public String teamName() {
        return team_name_input.getText().toString();
    }

    @Override
    public String teamDescription() {
        return description_input.getText().toString();
    }

    @Override
    public List<UserChip> selectedTeamMember() {
        return (List<UserChip>) chipsInput.getSelectedChipList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.create_team_button)
    public void onCreateteam(View v) {
        materialDialog = dialogHelper.loadingDialog("Processing", "Creating your Team");
        addTeamPresenter.createTeam();
    }
}
