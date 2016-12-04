package com.group7.YelpDataReader;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by cabeggar on 12/3/16.
 */
@RequiredArgsConstructor
public class UserReader {

    private final String filepath;

    HashMap<String, HashSet<String>> findElites() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        HashMap<String, HashSet<String>> elites = new HashMap<>();

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            User user = objectMapper.readValue(line, User.class);
            if (user.getElite().size() > 0) {
                elites.put(user.getUser_id(), new HashSet<>(user.getElite()));
                System.out.println(String.valueOf(elites.size()) + ": " + user.getElite().toString());
            }
        }

        return elites;
    }

}
