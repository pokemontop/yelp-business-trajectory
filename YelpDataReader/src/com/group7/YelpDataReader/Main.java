package com.group7.YelpDataReader;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;

public class Main {

    public static void main(String[] args) {
        System.out.println("Business");
        BusinessReader businessReader = new BusinessReader("yelp_dataset_challenge/business.json");
        HashSet<String> businessIDs = new HashSet<>();
        try {
            businessReader.initDBConnection();
            businessIDs = businessReader.readBusiness();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Elite");
        UserReader userReader = new UserReader("yelp_dataset_challenge/user.json");
        HashMap<String, HashSet<String>> elites = new HashMap<>();
        try {
            elites = userReader.findElites();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Review");
        ReviewReader reviewReader = new ReviewReader("yelp_dataset_challenge/review.json");
        reviewReader.setBusinessIDs(businessIDs);
        reviewReader.setElites(elites);
        try {
            reviewReader.initDBConnection();
            reviewReader.readReviews();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
