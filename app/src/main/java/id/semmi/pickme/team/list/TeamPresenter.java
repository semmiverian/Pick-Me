package id.semmi.pickme.team.list;

import java.util.List;

import id.semmi.pickme.BasicPresenter;
import id.semmi.pickme.team.Team;

/**
 * Created by Semmiverian on 4/23/17.
 */

public interface TeamPresenter extends BasicPresenter {
    void fetchUserTeam ();
    void setView (TeamListView teamListView);
    List<Team> resetTeamData();
}
