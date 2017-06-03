package id.semmi.pickme.team.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import id.semmi.pickme.DialogHelper;
import id.semmi.pickme.R;
import id.semmi.pickme.dagger.PickMeApplication;
import id.semmi.pickme.team.Team;
import id.semmi.pickme.team.addteam.AddTeamActivity;

public class TeamListActivity extends AppCompatActivity implements TeamListView, TeamAdapter.OnTeamClickListener {
    private static final String TAG = TeamListActivity.class.toString();
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.fab) FloatingActionButton fab;
    @Inject TeamPresenter teamPresenter;

    private TeamAdapter mTeamAdapter;
    private List<Team> mTeams = new ArrayList<>();
    private Unbinder unbinder;
    private DialogHelper dialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);
        unbinder = ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((PickMeApplication) getApplication()).getApplicationComponent().inject(this);

        mTeamAdapter = new TeamAdapter(this, mTeams);
        mTeamAdapter.onTeamClickListener(this);
        recyclerView.setAdapter(mTeamAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dialogHelper = new DialogHelper(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        teamPresenter.setView(this);
        teamPresenter.resume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void fetchUserTeam(List<Team> teams) {
        for (int i = 0; i < teams.size(); i++) {
            mTeams.add(teams.get(i));
            mTeamAdapter.notifyItemInserted(i);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mTeams.clear();
        mTeamAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTeams.clear();
        mTeamAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onError(String message) {
        dialogHelper.singlePositiveDialog("Error", message, "Ok", new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        });
    }

    @OnClick(R.id.fab)
    public void onFabClick (View v) {
        Intent addNewTeamIntent = new Intent(this, AddTeamActivity.class);
        startActivity(addNewTeamIntent);
    }

    @Override
    public void onTeamClick(View v, int position) {

    }
}
