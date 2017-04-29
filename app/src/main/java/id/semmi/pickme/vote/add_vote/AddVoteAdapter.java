package id.semmi.pickme.vote.add_vote;

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

/**
 * Created by Semmiverian on 4/29/17.
 */

public class AddVoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mContext;
    private final List<Vote> mVoteList;

    private static final int BASIC_VIEW = 0;
    private static final int ADD_MORE_VIEW = 1;

    private OnAddMoreItemListener listener;

    public AddVoteAdapter(Context mContext, List<Vote> mVoteList) {
        this.mContext = mContext;
        this.mVoteList = mVoteList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        switch (viewType) {
            case BASIC_VIEW:
                View addVoteView = inflater.inflate(R.layout.vote_option_list, parent, false);
                return new ViewHolder(addVoteView);
            case ADD_MORE_VIEW:
            default:
                View addMoreView = inflater.inflate(R.layout.vote_add_option, parent, false);
                return new AddViewHolder(addMoreView);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {
            case BASIC_VIEW:
                Vote vote = mVoteList.get(position);
                ViewHolder holderBasic = (ViewHolder) holder;
                holderBasic.voteText.setText(vote.getText());
                break;
            case ADD_MORE_VIEW:
            default:
                AddViewHolder holderAdd = (AddViewHolder) holder;
        }
    }


    @Override
    public int getItemViewType(int position) {
        return (position == mVoteList.size() - 1) ? ADD_MORE_VIEW : BASIC_VIEW;
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

    class AddViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.addCardView)
        CardView addCardView;


         AddViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.addCardView)
        void onAddMoreClick (View v) {
            if (listener != null) {
                int position = getLayoutPosition();

                listener.onAddMoreItemListener(v, position);
            }
        }
    }

    interface OnAddMoreItemListener {
        void onAddMoreItemListener(View view, int position);
    }

    void setAddMoreItemListener (OnAddMoreItemListener listener) {
        this.listener = listener;
    }

}
