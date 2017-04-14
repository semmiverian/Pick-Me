package id.semmi.pickme.dagger;

import javax.inject.Singleton;

import dagger.Component;
import id.semmi.pickme.login.LoginActivity;

/**
 * Created by Semmiverian on 4/14/17.
 */

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject (PickMeApplication target);
    void inject (LoginActivity target);
}

