package id.semmi.pickme.vote.vote_detail;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.semmi.pickme.R;
import id.semmi.pickme.vote.add_vote.Vote;


public class VoteListAdapter extends RecyclerView.Adapter<VoteListAdapter.ViewHolder> {
    private final Context mContext;
    private final List<Vote> mVoteList;

    public VoteListAdapter(Context mContext, List<Vote> mVoteList) {
        this.mContext = mContext;
        this.mVoteList = mVoteList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View addVoteView = inflater.inflate(R.layout.vote_option_list, parent, false);
        return new ViewHolder(addVoteView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Vote vote = mVoteList.get(position);
        holder.voteText.setText(vote.getText());
    }

    @Override
    public int getItemCount() {
        return mVoteList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.vote_text)
        AppCompatTextView voteText;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
