package com.marinamalynkina.android.nearbyplaces.data.network.models.response;

import java.io.Serializable;

/**
 * Created by ilmarin on 11.10.16.
 */
@SuppressWarnings("serial")
public class PhotoInfo implements Serializable {

    private int height;

    private int wifth;

    private String photo_reference;

    public PhotoInfo(int height, String photo_reference, int wifth) {
        this.height = height;
        this.photo_reference = photo_reference;
        this.wifth = wifth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWifth() {
        return wifth;
    }

    public void setWifth(int wifth) {
        this.wifth = wifth;
    }

    public String getPhoto_reference() {
        return photo_reference;
    }

    public void setPhoto_reference(String photo_reference) {
        this.photo_reference = photo_reference;
    }


}
