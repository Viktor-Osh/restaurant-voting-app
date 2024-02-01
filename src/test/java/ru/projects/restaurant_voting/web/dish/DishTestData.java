package ru.projects.restaurant_voting.web.dish;

import ru.projects.restaurant_voting.model.Dish;
import ru.projects.restaurant_voting.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.projects.restaurant_voting.web.restaurant.RestaurantTestData.restaurant1;
import static ru.projects.restaurant_voting.web.restaurant.RestaurantTestData.restaurant2;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");


    public static final int DISH1_ID = 1;
    public static final int DISH2_ID = 2;
    public static final int DISH3_ID = 3;
    public static final int DISH4_ID = 4;
    private static final int DISH5_ID = 5;
    public static final int DISH8_ID = 8;
    public static final int DISH9_ID = 9;
    public static final int DISH10_ID = 10;
    private static final int DISH6_ID = 6;
    private static final int DISH7_ID = 7;
    private static final int DISH11_ID = 11;
    private static final int DISH12_ID = 12;
    private static final int DISH13_ID = 13;

    //Dishes by restaurant 1
    public static final Dish dish1 = new Dish(DISH1_ID, LocalDate.of(2024, 1, 1), 10.5D, "fried eggs", restaurant1);
    public static final Dish dish2 = new Dish(DISH2_ID, LocalDate.of(2024, 1, 1), 15.1D, "hamburger", restaurant1);
    public static final Dish dish3 = new Dish(DISH3_ID, LocalDate.of(2024, 1, 1), 10D, "fried potato", restaurant1);
    public static final Dish dish4 = new Dish(DISH4_ID, LocalDate.of(2024, 1, 1), 6D, "coffee", restaurant1);
    public static final Dish dish8 = new Dish(DISH8_ID, LocalDate.now(), 14D, "borsch", restaurant1);
    public static final Dish dish9 = new Dish(DISH9_ID, LocalDate.now(), 12.1D, "mushroom soup", restaurant1);
    public static final Dish dish10 = new Dish(DISH10_ID, LocalDate.now(), 14D, "popcorn", restaurant1);

    //Dishes by restaurant 2
    public static final Dish dish5 = new Dish(DISH5_ID, LocalDate.of(2024, 01, 01), 15D, "khinkali", restaurant2);
    public static final Dish dish6 = new Dish(DISH6_ID, LocalDate.of(2024, 01, 01), 10D, "plov", restaurant2);
    public static final Dish dish7 = new Dish(DISH7_ID, LocalDate.of(2024, 01, 01), 4.4D, "juice", restaurant2);
    public static final Dish dish11 = new Dish(DISH11_ID, LocalDate.now(), 11D, "beef steak", restaurant2);
    public static final Dish dish12 = new Dish(DISH12_ID, LocalDate.now(), 12D, "dranniki", restaurant2);
    public static final Dish dish13 = new Dish(DISH13_ID, LocalDate.now(), 4.4D, "juice", restaurant2);


    public static final List<Dish> dishesByRestaurant1 = List.of(dish1, dish2, dish3, dish4, dish8, dish9, dish10);
    public static final List<Dish> dishesByRestaurant2 = List.of(dish5, dish6, dish7, dish11, dish12, dish13);
    public static final List<Dish> allDishes = Stream.concat(dishesByRestaurant1.stream(), dishesByRestaurant2.stream()).collect(Collectors.toList());
    public static final List<Dish> dishesByDate = List.of(dish1, dish2, dish3, dish4, dish5, dish6, dish7);

    public static Dish getNew() {
        return new Dish(null, LocalDate.now(), 111.4, "new food", restaurant1);
    }

    public static Dish getUpdated() {
        return new Dish(DISH1_ID, LocalDate.now(), 123D, "updated", restaurant2);
    }

}
