package ru.projects.restaurant_voting.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends NamedTo {


    Integer rating;

    public RestaurantTo(Integer id, String name, Integer rating) {
        super(id, name);
        this.rating = rating;
    }
}
