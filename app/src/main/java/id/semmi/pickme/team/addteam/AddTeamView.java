package id.semmi.pickme.team.addteam;

import java.util.List;

/**
 * Created by Semmiverian on 4/16/17.
 */

public interface AddTeamView {
    void fetchUsersData(List<UserChip> userChips);
    void onSuccessCreatingTeam(String message);
    void onFailedCreatingTeam(String message);
    String teamName();
    String teamDescription();
    List<UserChip> selectedTeamMember();
}
