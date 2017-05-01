package id.semmi.pickme.team.addteam;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import id.semmi.pickme.team.TeamRepository;

/**
 * Created by Semmiverian on 4/16/17.
 */

public class AddTeamPresenterImpl implements AddTeamPresenter {

    private FirebaseAuth firebaseAuth;
    private TeamRepository teamRepository;
    private AddTeamView mAddTeamView;

    public AddTeamPresenterImpl(FirebaseAuth firebaseAuth, TeamRepository teamRepository) {
        this.firebaseAuth = firebaseAuth;
        this.teamRepository = teamRepository;
    }

    @Override
    public void resume() {
    }

    @Override
    public void destroy() {

    }

    @Override
    public void setView(AddTeamView addTeamView) {
        mAddTeamView = addTeamView;
    }

    @Override
    public void createTeam() {
        teamRepository.createAndSendInvitation(mAddTeamView.teamName(), mAddTeamView.teamDescription(), mAddTeamView.selectedTeamMember(), new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (!task.isSuccessful()) {
                    mAddTeamView.onFailedCreatingTeam(task.getException().getMessage());
                    return;
                }
                mAddTeamView.onSuccessCreatingTeam("Successfully creating new team, Your Friend need to accept the invitation before joining the team");
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mAddTeamView.onFailedCreatingTeam(e.getMessage());
            }
        });

    }

    @Override
    public void fetchUserChip(final Drawable drawable) {
        teamRepository.findUser(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<UserChip> userChips = new ArrayList<>();

                FirebaseUser user = firebaseAuth.getCurrentUser();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    if (!user.getUid().equals(userSnapshot.getKey())) {
                        UserChip userChip = userSnapshot.getValue(UserChip.class);
                        userChips.add(new UserChip(userChip.getUuid(), userChip.getName(), userChip.getEmail(), null));
                    }
                }
                mAddTeamView.fetchUsersData(userChips);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mAddTeamView.onFailedCreatingTeam(databaseError.getMessage());
            }
        });
    }
}
