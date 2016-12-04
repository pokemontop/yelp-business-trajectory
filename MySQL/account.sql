create user 'yelpimport'@'localhost' identified by 'yelp-data-import';
grant all on yelp.* to 'yelpimport'@'localhost';