package id.semmi.pickme.dagger;

import android.app.Application;


/**
 * Created by Semmiverian on 4/14/17.
 */

public class PickMeApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                                .applicationModule(new ApplicationModule(this))
                                 .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
