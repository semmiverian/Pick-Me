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
 * Created by Semmiverian on 4/30/17.
 */

public class TeamRepositoryImpl implements TeamRepository {
    private final DatabaseReference databaseReference;
    private final FirebaseAuth firebaseAuth;
    private Map<String, Object> mappedUser;

    public TeamRepositoryImpl(Firebase firebase) {
        this.databaseReference = firebase.getDatabaseReference();
        this.firebaseAuth = firebase.getFirebaseAuth();
        FirebaseUser loggedInUser = firebaseAuth.getCurrentUser();
        User user = new User(loggedInUser.getDisplayName(), loggedInUser.getUid(), loggedInUser.getEmail());
        mappedUser = user.userToMap();
    }

    @Override
    public void findUser(ValueEventListener listener) {
        databaseReference.child("users").addListenerForSingleValueEvent(listener);
    }

    @Override
    public void createAndSendInvitation(String name, String description, List<UserChip> userChips, OnCompleteListener listener, OnFailureListener listenerError) {
        String key = databaseReference.child("teams").push().getKey();
        String currentUserId = firebaseAuth.getCurrentUser().getUid();
        Invitation invitation = new Invitation(name, description);
        Team team = new Team(name, description);
        Map<String, Object> userMap = new HashMap<>();

        databaseReference.child("teams/" + key).setValue(team);
        userMap.put("/users/" + currentUserId + "/teams/" + key, team);

        userChips.add(new UserChip(currentUserId, firebaseAuth.getCurrentUser().getDisplayName(),firebaseAuth.getCurrentUser().getEmail(), null));

        for (UserChip userChip : userChips) {
            String notificationKey = databaseReference.child("notifications/" + userChip.getUuid()).push().getKey();
            String userKey = databaseReference.child("teams/" + key + "/user").push().getKey();

            databaseReference.child("notifications/" + userChip.getUuid() + "/" + notificationKey).setValue(invitation);

            userMap.put("notifications/" + userChip.getUuid() + "/" + notificationKey + "/user", mappedUser);
            userMap.put("teams/" + key + "/user/" + userKey, userChip.userToMap());
        }

        databaseReference.updateChildren(userMap)
                .addOnCompleteListener(listener)
                .addOnFailureListener(listenerError);
    }

    @Override
    public void getLoggedInUserTeams(ValueEventListener listener) {
        String currentUserId = firebaseAuth.getCurrentUser().getUid();

        databaseReference.child("/users/" + currentUserId + "/teams").addListenerForSingleValueEvent(listener);
    }
}
