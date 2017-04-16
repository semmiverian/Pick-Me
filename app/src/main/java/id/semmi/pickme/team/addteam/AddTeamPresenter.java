package id.semmi.pickme.team.addteam;

import android.graphics.drawable.Drawable;

import id.semmi.pickme.BasicPresenter;

/**
 * Created by Semmiverian on 4/16/17.
 */

public interface AddTeamPresenter extends BasicPresenter {
    void setView (AddTeamView addTeamView);
    void createTeam();
    void fetchUserChip(Drawable drawable);
}
