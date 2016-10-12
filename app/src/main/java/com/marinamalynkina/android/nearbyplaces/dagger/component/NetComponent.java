package com.marinamalynkina.android.nearbyplaces.dagger.component;

import com.marinamalynkina.android.nearbyplaces.dagger.module.AppModule;
import com.marinamalynkina.android.nearbyplaces.dagger.module.NetModule;
import com.marinamalynkina.android.nearbyplaces.ui.MapsActivity;
import com.marinamalynkina.android.nearbyplaces.ui.PlaceActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(MapsActivity activity);
    void inject(PlaceActivity activity);
}
