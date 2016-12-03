package com.group7.YelpDataReader;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by cabeggar on 12/2/16.
 *
 * This class reads Business class from business.json from yelp_dataset_challenge, parse its food type, and import the
 * information into the database.
 *
 */

@RequiredArgsConstructor
class BusinessReader {

    private final String filepath;

    void startReading() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            Business business = objectMapper.readValue(line, Business.class);
            System.out.println(business.getCategories());
        }
    }
}
