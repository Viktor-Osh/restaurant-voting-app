package ru.projects.restaurant_voting.util;

import lombok.experimental.UtilityClass;
import ru.projects.restaurant_voting.model.Restaurant;
import ru.projects.restaurant_voting.to.RestaurantTo;

@UtilityClass
public class RestaurantUlti {
    public static Restaurant createNewFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(null, restaurantTo.getName(), restaurantTo.getRating());
    }

    public static Restaurant updateFromTo(Restaurant restaurant, RestaurantTo restaurantTo) {
        restaurant.setName(restaurantTo.getName());
        restaurant.setRating(restaurantTo.getRating());
        return restaurant;
    }
}
