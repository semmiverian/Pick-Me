package id.semmi.pickme.dagger;

import android.app.Application;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import id.semmi.pickme.R;
import id.semmi.pickme.login.LoginPresenter;
import id.semmi.pickme.login.LoginPresenterImpl;

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
    public LoginPresenter loginPresenter () {
        return new LoginPresenterImpl();
    }
}
