package com.group7.YelpDataReader;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

/**
 * Created by cabeggar on 12/3/16.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String user_id;
    private String name;
    private int review_count;
    private float average_stars;
    private ArrayList<String> elite;
    private int fans;

}
