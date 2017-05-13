package id.semmi.pickme.vote.vote_detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.semmi.pickme.R;
import id.semmi.pickme.dagger.PickMeApplication;
import id.semmi.pickme.model.User;
import id.semmi.pickme.vote.Votes;
import id.semmi.pickme.vote.add_vote.Vote;

public class VoteDetailsActivity extends AppCompatActivity implements DetailVoteView, VoteListAdapter.OnChoseItemListener {
    private static final String TAG = VoteDetailsActivity.class.toString();
    @BindView(R.id.voteRecyclerView) RecyclerView voteRecyclerView;
    @BindView(R.id.voteCloseInfo) AppCompatButton voteCloseInfo;
    @BindView(R.id.openDiscussionButton) AppCompatButton openDiscussionButton;
    @BindView(R.id.detailButtons) AppCompatButton detailButtons;
    @BindView(R.id.alreadyVoteRecyclerView) RecyclerView alreadyVoteRecyclerView;
    @BindView(R.id.notAlreadyVoteRecyclerView) RecyclerView notAlreadyVoteRecyclerView;
    @BindView(R.id.voteTitle) AppCompatTextView voteTitle;
    @BindView(R.id.voteDescription) AppCompatTextView voteDescription;
    @Inject DetailVotePresenter detailVotePresenter;
    private Unbinder unbinder;
    private List<Vote> mVotes = new ArrayList<>();
    private List<User> mUsers = new ArrayList<>();
    private VoteListAdapter mVoteListAdapter;
    private UserAdapter mUserAdapter;
    private boolean allowedToVote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_details_actiivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        unbinder = ButterKnife.bind(this);
        ((PickMeApplication) getApplication()).getApplicationComponent().inject(this);
        mVoteListAdapter = new VoteListAdapter(this, mVotes);
        voteRecyclerView.setAdapter(mVoteListAdapter);
        voteRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mVoteListAdapter.setOnItemClickListener(this);

        mUserAdapter = new UserAdapter(this, mUsers);
        notAlreadyVoteRecyclerView.setAdapter(mUserAdapter);
        notAlreadyVoteRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        alreadyVoteRecyclerView.setAdapter(mUserAdapter);
        alreadyVoteRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    protected void onResume() {
        super.onResume();
        detailVotePresenter.setView(this);
        detailVotePresenter.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void fetchVoteDetail(Votes votes) {
        voteTitle.setText(votes.getName());
        voteDescription.setText(votes.getText());
        voteCloseInfo.setText(votes.getEndDate());

        for (int i = 0; i < votes.getVotes().size(); i++) {
            mVotes.add(votes.getVotes().get(i));
            mVoteListAdapter.notifyItemInserted(i);
        }

        int pos = mUsers.size();
        for (HashMap.Entry<String, User> map : votes.getPendingVoteUsers().entrySet()) {
            mUsers.add(map.getValue());
            mUserAdapter.notifyItemInserted(pos);
            pos++;
        }
    }

    @Override
    public boolean allowedToVotes(boolean status) {
        this.allowedToVote = status;
        Log.d(TAG, "allowedToVotes: " + status);
        return status;
    }

    @Override
    public void onChoseItemListener(View v, int position) {
        Log.d(TAG, "onChoseItemListener: " + mVotes.get(position).getText());
        if (this.allowedToVote) {
            detailVotePresenter.setUserVote(mVotes.get(position), position);
        }
        Toast.makeText(this, "You've Already Voted ^_^", Toast.LENGTH_SHORT).show();
    }
}
