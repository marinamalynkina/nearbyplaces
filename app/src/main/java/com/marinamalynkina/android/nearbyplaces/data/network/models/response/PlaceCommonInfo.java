package com.marinamalynkina.android.nearbyplaces.data.network.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ilmarin on 10.10.16.
 */

public class PlaceCommonInfo {

    private Geometry geometry;

    private String icon;

    private String name;

    @SerializedName("opening_hours")
    private OpeningHours openingHours;

    private ArrayList<PhotoInfo> photos;

    private String place_id;

    private String scope;

    private String reference;

    private String vicinity;




//    "geometry" : {
//        "location" : {
//            "lat" : -33.870775,
//                    "lng" : 151.199025
//        }
//    },
//            "icon" : "http://maps.gstatic.com/mapfiles/place_api/icons/travel_agent-71.png",
//            "id" : "21a0b251c9b8392186142c798263e289fe45b4aa",
//            "name" : "Rhythmboat Cruises",
//            "opening_hours" : {
//        "open_now" : true
//    },
//            "photos" : [
//    {
//        "height" : 270,
//            "html_attributions" : [],
//        "photo_reference" : "CnRnAAAAF-LjFR1ZV93eawe1cU_3QNMCNmaGkowY7CnOf-kcNmPhNnPEG9W979jOuJJ1sGr75rhD5hqKzjD8vbMbSsRnq_Ni3ZIGfY6hKWmsOf3qHKJInkm4h55lzvLAXJVc-Rr4kI9O1tmIblblUpg2oqoq8RIQRMQJhFsTr5s9haxQ07EQHxoUO0ICubVFGYfJiMUPor1GnIWb5i8",
//            "width" : 519
//    }
//    ],
//            "place_id" : "ChIJyWEHuEmuEmsRm9hTkapTCrk",
//            "scope" : "GOOGLE",
//            "alt_ids" : [
//    {
//        "place_id" : "D9iJyWEHuEmuEmsRm9hTkapTCrk",
//            "scope" : "APP"
//    }
//    ],
//            "reference" : "CoQBdQAAAFSiijw5-cAV68xdf2O18pKIZ0seJh03u9h9wk_lEdG-cP1dWvp_QGS4SNCBMk_fB06YRsfMrNkINtPez22p5lRIlj5ty_HmcNwcl6GZXbD2RdXsVfLYlQwnZQcnu7ihkjZp_2gk1-fWXql3GQ8-1BEGwgCxG-eaSnIJIBPuIpihEhAY1WYdxPvOWsPnb2-nGb6QGhTipN0lgaLpQTnkcMeAIEvCsSa0Ww",
//            "types" : [ "travel_agency", "restaurant", "food", "establishment" ],
//            "vicinity" : "Pyrmont Bay Wharf Darling Dr, Sydney"


    public Geometry getGeometry() {
        return geometry;
    }

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public ArrayList<PhotoInfo> getPhotos() {
        return photos;
    }

    public String getPlace_id() {
        return place_id;
    }

    public String getReference() {
        return reference;
    }

    public String getScope() {
        return scope;
    }

    public String getVicinity() {
        return vicinity;
    }
}
