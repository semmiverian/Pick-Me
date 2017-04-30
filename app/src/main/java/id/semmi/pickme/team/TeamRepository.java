package id.semmi.pickme.team;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.semmi.pickme.firebase.Firebase;
import id.semmi.pickme.model.User;
import id.semmi.pickme.team.addteam.UserChip;

/**
 * Created by Semmiverian on 4/16/17.
 */

public interface TeamRepository {
    void findUser (ValueEventListener listener);
    void createAndSendInvitation(String name, String description, List<UserChip> userChips, OnCompleteListener listener, OnFailureListener listenerError);
    void getLoggedInUserTeams (ValueEventListener listener);

}
