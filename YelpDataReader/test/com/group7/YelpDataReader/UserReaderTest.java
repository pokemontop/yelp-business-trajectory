package com.group7.YelpDataReader;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by cabeggar on 12/3/16.
 */
public class UserReaderTest {

    private UserReader userReader;

    @Before
    public void init() {
        userReader = new UserReader("yelp_dataset_test/user.json");
    }

    @Test
    public void startReadingTest() throws IOException {
        userReader.startReading();
    }

}
