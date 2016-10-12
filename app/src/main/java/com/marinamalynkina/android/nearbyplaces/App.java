package com.marinamalynkina.android.nearbyplaces;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.marinamalynkina.android.nearbyplaces.dagger.component.DaggerNetComponent;
import com.marinamalynkina.android.nearbyplaces.dagger.component.NetComponent;
import com.marinamalynkina.android.nearbyplaces.dagger.module.AppModule;
import com.marinamalynkina.android.nearbyplaces.dagger.module.NetModule;

public class App extends Application {

    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);

        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule("https://maps.googleapis.com/maps/"))
                .build();
    }


    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
