package com.group7.YelpDataReader;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by cabeggar on 12/3/16.
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class Review {

    private String business_id;
    private String user_id;
    private float stars;
    private String text;
    private String date;

}
