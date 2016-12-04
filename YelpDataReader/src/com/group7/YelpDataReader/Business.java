package com.group7.YelpDataReader;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

/**
 * Created by cabeggar on 12/2/16.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class Business {

    private String business_id;
    private String name;
    private String full_address;
    private String city;
    private String state;
    private double longitude;
    private double latitude;
    private float stars;
    private int review_count;
    private ArrayList<String> categories;
    private boolean open;

}
