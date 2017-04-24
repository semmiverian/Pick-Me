package id.semmi.pickme.team.list;

import java.util.List;

import id.semmi.pickme.BasicView;
import id.semmi.pickme.team.Team;

/**
 * Created by Semmiverian on 4/19/17.
 */

public interface TeamListView extends BasicView {
    void fetchUserTeam(List<Team> teams);

}
