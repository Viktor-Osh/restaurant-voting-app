package ru.projects.restaurant_voting.web.restaurant;

import ru.projects.restaurant_voting.model.Restaurant;
import ru.projects.restaurant_voting.web.MatcherFactory;

import java.util.List;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "dishes");

    public static final int RESTAURANT1_ID = 1;
    public static final int RESTAURANT2_ID = 2;
    public static final int NOT_FOUND = 111;
    public static final String RESTAURANT1_NAME = "KFC";
    public static final String RESTAURANT2_NAME = "GOURMAND";

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, RESTAURANT1_NAME, "Moscow, Red Square");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT2_ID, RESTAURANT2_NAME, "Moscow, Pushkina st. 6");
    public static final List<Restaurant> restaurants = List.of(restaurant1, restaurant2);

    public static Restaurant getNew() {
        return new Restaurant(null, "New", "new address");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "UpdatedName", "Moscow, Red Square");
    }


}
