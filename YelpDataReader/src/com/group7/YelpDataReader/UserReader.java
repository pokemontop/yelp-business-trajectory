package com.group7.YelpDataReader;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by cabeggar on 12/3/16.
 */
@RequiredArgsConstructor
public class UserReader {

    private final String filepath;

    void startReading() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            User user = objectMapper.readValue(line, User.class);
            System.out.println(user.getElite());
        }
    }

}
