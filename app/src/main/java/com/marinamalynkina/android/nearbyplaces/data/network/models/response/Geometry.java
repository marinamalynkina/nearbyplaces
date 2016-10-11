package com.marinamalynkina.android.nearbyplaces.data.network.models.response;

import java.io.Serializable;

/**
 * Created by ilmarin on 11.10.16.
 */
@SuppressWarnings("serial")
public class Geometry implements Serializable {

    private Location location;

    public Geometry(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
