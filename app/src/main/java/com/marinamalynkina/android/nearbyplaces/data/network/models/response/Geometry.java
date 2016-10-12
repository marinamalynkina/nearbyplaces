package com.marinamalynkina.android.nearbyplaces.data.network.models.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by ilmarin on 11.10.16.
 */
public class Geometry implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.location, flags);
    }

    protected Geometry(Parcel in) {
        this.location = (Location) in.readParcelable(Location.class.getClassLoader());
    }

    public static final Parcelable.Creator<Geometry> CREATOR = new Parcelable.Creator<Geometry>() {
        @Override
        public Geometry createFromParcel(Parcel source) {
            return new Geometry(source);
        }

        @Override
        public Geometry[] newArray(int size) {
            return new Geometry[size];
        }
    };
}
