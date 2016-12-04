package com.group7.YelpDataReader;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
    private final String databaseURL = "jdbc:mysql://localhost:3306/yelp?verifyServerCertificate=false&useSSL=false";
    private final String user = "yelpimport";
    private final String password = "yelp-data-import";
    private Connection connection;
    private PreparedStatement insertBusiness;
    private PreparedStatement insertFoodType;

    void initDBConnection() throws SQLException {
        connection = DriverManager.getConnection(databaseURL, user, password);
        insertBusiness = connection.prepareStatement(
                "INSERT INTO business(bid, bname, baddr, city, state, longitude, latitude, stars, review_count, open)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        insertFoodType = connection.prepareStatement("INSERT INTO foodtype(bid, ftype) VALUES (?, ?)");
    }

    HashSet<String> readBusiness() throws IOException, SQLException {
        HashSet<String> businessIDs = new HashSet<>();
        ObjectMapper objectMapper = new ObjectMapper();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        HashMap<String, HashSet<String>> typeMapping = initTypeMapping();

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            Business business = objectMapper.readValue(line, Business.class);

            if (connection == null || insertBusiness == null || insertFoodType == null) {
                System.out.println(business.getName());
                continue;
            }

            HashSet<String> catSet = new HashSet<>(business.getCategories());
            ArrayList<String> foodTypes = new ArrayList<>();
            for (String foodType : typeMapping.keySet()) {
                HashSet<String> tmp = new HashSet<>(catSet);
                tmp.retainAll(typeMapping.get(foodType));
                if (tmp.size() > 0) {
                    foodTypes.add(foodType);
                }
            }

            if (foodTypes.size() > 0) {
                insertBusiness.setString(1, business.getBusiness_id());
                insertBusiness.setString(2, business.getName());
                insertBusiness.setString(3, business.getFull_address());
                insertBusiness.setString(4, business.getCity());
                insertBusiness.setString(5, business.getState());
                insertBusiness.setDouble(6, business.getLongitude());
                insertBusiness.setDouble(7, business.getLatitude());
                insertBusiness.setFloat(8, business.getStars());
                insertBusiness.setInt(9, business.getReview_count());
                insertBusiness.setBoolean(10, business.isOpen());

                insertBusiness.executeUpdate();

                for (String type : foodTypes) {
                    insertFoodType.setString(1, business.getBusiness_id());
                    insertFoodType.setString(2, type);

                    insertFoodType.executeUpdate();
                }

                businessIDs.add(business.getBusiness_id());
                System.out.println(String.valueOf(businessIDs.size()) + ": " + business.getName());
            }
        }

        connection.close();
        return businessIDs;
    }

    private HashMap<String, HashSet<String>> initTypeMapping() {
        HashMap<String, HashSet<String>> mapping = new HashMap<>();

        HashSet<String> categories1 = new HashSet<>();
        categories1.add("American (New)");
        categories1.add("American (Traditional)");
        categories1.add("British");
        categories1.add("Fish & Chips");
        categories1.add("Irish");
        categories1.add("Scottish");
        categories1.add("Southern");
        mapping.put("American and British", categories1);

        HashSet<String> categories2 = new HashSet<>();
        categories2.clear();
        categories2.add("Fast Food");
        categories2.add("Burgers");
        categories2.add("Waffles");
        categories2.add("Hot Dogs");
        categories2.add("Mexican");
        categories2.add("New Mexican Cuisine");
        categories2.add("Pizza");
        categories2.add("Salad");
        categories2.add("Sandwiches");
        categories2.add("Steakhouse");
        categories2.add("Tex-mex");
        categories2.add("Comfort Food");
        mapping.put("Comfort Food", categories2);

        HashSet<String> categories3 = new HashSet<>();
        categories3.add("Afghan");
        categories3.add("Arabian");
        categories3.add("Halal");
        categories3.add("Middle Eastern");
        categories3.add("Persian/Iranian");
        categories3.add("Syrian");
        categories3.add("Turkish");
        categories3.add("Uzbek");
        mapping.put("Halal", categories3);

        HashSet<String> categories4 = new HashSet<>();
        categories4.add("Bangladeshi");
        categories4.add("Himalayan/Nepalese");
        categories4.add("Pakistani");
        categories4.add("Sri Lankan");
        mapping.put("Indian", categories4);

        HashSet<String> categories5 = new HashSet<>();
        categories5.add("Burmese");
        categories5.add("Cambodian");
        categories5.add("Filipino");
        categories5.add("Indonesian");
        categories5.add("Indian");
        categories5.add("Laotian");
        categories5.add("Thai");
        categories5.add("Vietnamese");
        mapping.put("Southeast Asian", categories5);

        HashSet<String> categories6 = new HashSet<>();
        categories6.add("Cajun/Creole");
        categories6.add("Creperies");
        categories6.add("French");
        mapping.put("French", categories6);

        HashSet<String> categories7 = new HashSet<>();
        categories7.add("Italian");
        mapping.put("Italian", categories7);

        HashSet<String> categories8 = new HashSet<>();
        categories8.add("Japanese");
        categories8.add("Sushi Bars");
        categories8.add("Korean");
        mapping.put("Japanese and Korean", categories8);

        HashSet<String> categories9 = new HashSet<>();
        categories9.add("Catalan");
        categories9.add("Iberian");
        categories9.add("Basque");
        categories9.add("Portuguese");
        categories9.add("Spanish");
        categories9.add("Tapas Bars");
        categories9.add("Tapas/Small Plates");
        mapping.put("Spanish", categories9);

        HashSet<String> categories10 = new HashSet<>();
        categories10.add("Chinese");
        categories10.add("Hong Kong Style Cafe");
        categories10.add("Hot Pot");
        categories10.add("Mongolian");
        categories10.add("Noodles");
        categories10.add("Singapore");
        categories10.add("Taiwanese");
        mapping.put("Chinese", categories10);

        return mapping;
    }
}
