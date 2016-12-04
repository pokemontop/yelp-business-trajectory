package com.group7.YelpDataReader;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by cabeggar on 12/3/16.
 *
 * A test on BusinessReader class
 *
 */
public class BusinessReaderTest {
    
    private BusinessReader businessReader;

    @Before
    public void init() {
        businessReader = new BusinessReader("yelp_dataset_test/business.json");
    }

    @Test
    public void startReadingTest() throws IOException, SQLException {
        System.out.println(businessReader.readBusiness());
    }

}
