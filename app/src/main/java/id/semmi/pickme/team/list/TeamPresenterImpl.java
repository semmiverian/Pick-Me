package id.semmi.pickme.team.list;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import id.semmi.pickme.team.Team;
import id.semmi.pickme.team.TeamRepository;

/**
 * Created by Semmiverian on 4/23/17.
 */

public class TeamPresenterImpl implements TeamPresenter {

    private TeamRepository teamRepository;
    private TeamListView teamListView;

    public TeamPresenterImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public void resume() {
        fetchUserTeam();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void fetchUserTeam() {
        teamRepository.getLoggedInUserTeams(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Team> teamList = new ArrayList<>();
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    Team team = data.getValue(Team.class);
                    teamList.add(team);
                }
                teamListView.fetchUserTeam(teamList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                teamListView.onError(databaseError.getMessage());
            }
        });
    }

    @Override
    public void setView(TeamListView teamListView) {
        this.teamListView = teamListView;
    }

    @Override
    public List<Team> resetTeamData() {
        return new ArrayList<>();
    }
}
