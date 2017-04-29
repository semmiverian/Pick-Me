package id.semmi.pickme.dagger;

import android.app.Application;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import id.semmi.pickme.DialogHelper;
import id.semmi.pickme.R;
import id.semmi.pickme.firebase.Firebase;
import id.semmi.pickme.firebase.FirebaseImpl;
import id.semmi.pickme.login.LoginPresenter;
import id.semmi.pickme.login.LoginPresenterImpl;
import id.semmi.pickme.login.LoginRepository;
import id.semmi.pickme.register.RegisterPresenter;
import id.semmi.pickme.register.RegisterPresenterImpl;
import id.semmi.pickme.register.RegisterRepository;
import id.semmi.pickme.team.TeamRepository;
import id.semmi.pickme.team.addteam.AddTeamPresenter;
import id.semmi.pickme.team.addteam.AddTeamPresenterImpl;
import id.semmi.pickme.team.list.TeamPresenter;
import id.semmi.pickme.team.list.TeamPresenterImpl;
import id.semmi.pickme.vote.VoteRepository;
import id.semmi.pickme.vote.add_vote.AddVotePresenter;
import id.semmi.pickme.vote.add_vote.AddVotePresenterImpl;

/**
 * Created by Semmiverian on 4/14/17.
 */

@Module
public class ApplicationModule {
    Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides @Singleton
    public GoogleSignInOptions googleSignInOptions () {
        return  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("675313142627-9fpv0bp6eplitvqfffmb4vuep9pstu4j.apps.googleusercontent.com")
                .requestEmail()
                .build();
    }

    @Provides
    public FirebaseAuth firebaseAuth () {
        return FirebaseAuth.getInstance();
    }

    @Provides
    public DatabaseReference firebaseDatabaseReference () {
        return FirebaseDatabase.getInstance().getReference();
    }

    @Provides
    public Firebase firebaseContract (FirebaseAuth firebaseAuth, DatabaseReference databaseReference) {
        return new FirebaseImpl(firebaseAuth, databaseReference);
    }

    @Provides
    public RegisterRepository registerRepository (FirebaseAuth firebaseAuth, DatabaseReference databaseReference) {
        return new RegisterRepository(firebaseAuth, databaseReference);
    }

    @Provides
    public LoginRepository loginRepository (FirebaseAuth firebaseAuth, DatabaseReference databaseReference) {
        return new LoginRepository(firebaseAuth, databaseReference);
    }

    @Provides @Singleton
    public DialogHelper dialogHelper () {
        return new DialogHelper(application.getApplicationContext());
    }

    @Provides
    public LoginPresenter loginPresenter (LoginRepository loginRepository) {
        return new LoginPresenterImpl(loginRepository);
    }

    @Provides
    public RegisterPresenter registerPresenter (FirebaseAuth firebaseAuth, RegisterRepository registerRepository) {
        return new RegisterPresenterImpl(firebaseAuth, registerRepository);
    }

    @Provides
    public TeamRepository teamRepository (Firebase firebase) {
        return new TeamRepository(firebase);
    }

    @Provides
    public AddTeamPresenter addTeamPresenter (FirebaseAuth firebaseAuth, TeamRepository teamRepository) {
        return new AddTeamPresenterImpl(firebaseAuth, teamRepository);
    }

    @Provides
    public TeamPresenter teamPresenter (TeamRepository teamRepository) {
        return new TeamPresenterImpl(teamRepository);
    }

    @Provides
    public VoteRepository voteRepository (Firebase firebase) {
        return new VoteRepository(firebase);
    }

    @Provides
    public AddVotePresenter addVotePresenter (VoteRepository voteRepository) {
        return new AddVotePresenterImpl(voteRepository);
    }
}
