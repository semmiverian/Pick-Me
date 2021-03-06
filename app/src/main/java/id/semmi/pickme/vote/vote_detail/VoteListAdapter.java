package id.semmi.pickme.vote.vote_detail;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private Context mContext;
    private List<Vote> mVoteList;
    private OnChoseItemListener listener;

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
        String percentageCount =  (vote.getPercentage() == null) ? "0%" : vote.getPercentage().toString() + "%";
        holder.votePercentage.setText(percentageCount);
    }

    @Override
    public int getItemCount() {
        return mVoteList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.vote_text)
        AppCompatTextView voteText;
        @BindView(R.id.cardView) CardView cardView;
        @BindView(R.id.votePercentage) AppCompatTextView votePercentage;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.cardView)
        void onCardClick (View v) {
            int position = getLayoutPosition();
            if (listener != null) {
                listener.onChoseItemListener(v, position);
            }
        }
    }

    interface OnChoseItemListener {
        void onChoseItemListener(View v, int position);
    }

    public void setOnItemClickListener(OnChoseItemListener listener) {
        this.listener = listener;
    }
}
