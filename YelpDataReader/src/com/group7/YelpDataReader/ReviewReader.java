package com.group7.YelpDataReader;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by cabeggar on 12/3/16.
 */
@RequiredArgsConstructor
public class ReviewReader {

    private final String filepath;
    @Setter private HashSet<String> businessIDs;
    @Setter private HashMap<String, HashSet<String>> elites;

    private final String databaseURL = "jdbc:mysql://localhost:3306/yelp?verifyServerCertificate=false&useSSL=false";
    private final String user = "yelpimport";
    private final String password = "yelp-data-import";
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Connection connection;
    private PreparedStatement updateVisit;
    private PreparedStatement insertElite;

    void initDBConnection() throws SQLException {
        connection = DriverManager.getConnection(databaseURL, user, password);
        updateVisit = connection.prepareStatement(
                "insert into visit (bid, bdate, vcount)" +
                        "values (?, ?, 1)" +
                        "on duplicate key update vcount = vcount + 1");
        insertElite = connection.prepareStatement(
                "INSERT IGNORE INTO elite (uid, bid, vdate, rtext, rstar) " +
                        "VALUES (?, ?, ?, ?, ?)");
    }

    void readReviews() throws IOException, SQLException, ParseException {
        ObjectMapper objectMapper = new ObjectMapper();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));

        String prevBusinessID = null;
        int businessCount = 0;
        float progress = 0;
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            Review review = objectMapper.readValue(line, Review.class);

            if (connection == null || updateVisit == null || insertElite == null) {
                System.out.println(review.getVotes());
                continue;
            }

            if (businessIDs.contains(review.getBusiness_id())) {
                if (!review.getBusiness_id().equals(prevBusinessID)) {
                    prevBusinessID = review.getBusiness_id();
                    businessCount++;
                    progress = businessCount * 1.0f / businessIDs.size();
                }
                updateVisit.setString(1, review.getBusiness_id());
                Date date = new Date(dateFormat.parse(review.getDate()).getTime());
                updateVisit.setDate(2, date);
                updateVisit.executeUpdate();

                if (elites.containsKey(review.getUser_id()) && elites.get(review.getUser_id()).contains(review.getDate().substring(0, 4))) {
                    insertElite.setString(1, review.getUser_id());
                    insertElite.setString(2, review.getBusiness_id());
                    insertElite.setDate(3, date);
                    insertElite.setString(4, review.getText());
                    insertElite.setFloat(5, review.getStars());
                    insertElite.executeUpdate();
                    System.out.println(review.getUser_id() + " " + review.getDate() + " " + review.getBusiness_id() + " " + String.valueOf(progress));
                }
            }
        }
        connection.close();
    }

}
