package id.semmi.pickme.team.list;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.semmi.pickme.R;
import id.semmi.pickme.team.Team;

/**
 * Created by Semmiverian on 4/23/17.
 */

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder>{

    private Context mContext;
    private List<Team> mTeams;
    private OnTeamClickListener listener;

    public TeamAdapter(Context mContext, List<Team> mTeams) {
        this.mContext = mContext;
        this.mTeams = mTeams;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View skuView = inflater.inflate(R.layout.team_list_recyclerview, parent, false);
        return new ViewHolder(skuView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Team team = mTeams.get(position);
        holder.team_name.setText(team.getName());
    }

    @Override
    public int getItemCount() {
        return mTeams.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.team_icon) AppCompatImageView team_icon;
        @BindView(R.id.team_name) AppCompatTextView team_name;
        @BindView(R.id.team_info) AppCompatTextView team_info;
        @BindView(R.id.leave_team) AppCompatTextView leave_team;

         ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getLayoutPosition();

                        listener.onTeamClick(v, position);
                    }
                }
            });
        }
    }

    public void onTeamClickListener (OnTeamClickListener listener) {
        this.listener = listener;
    }

    interface OnTeamClickListener{
        void onTeamClick(View v, int position);
    }
}
