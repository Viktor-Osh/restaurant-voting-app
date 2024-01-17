package ru.projects.restaurant_voting.to;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.time.LocalDate;

@Value
public class DishTo extends NamedTo {

    @NotNull
    LocalDate menuDate;

    @NotNull
    Double price;

    @NotNull
    Integer restaurantId;

    public DishTo(Integer id, String name, LocalDate menuDate, Double price, Integer restaurantId) {
        super(id, name);
        this.menuDate = menuDate;
        this.price = price;
        this.restaurantId = restaurantId;
    }
}
