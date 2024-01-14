package ru.projects.restaurant_voting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.projects.restaurant_voting.model.Dish;
import ru.projects.restaurant_voting.repository.DishRepository;
import ru.projects.restaurant_voting.repository.RestaurantRepository;

@Service
@AllArgsConstructor
public class DishService {
    private final RestaurantRepository restaurantRepository;

    private final DishRepository dishRepository;

    @Transactional
    public Dish save(int restaurantId, Dish dish) {
        dish.setRestaurant(restaurantRepository.getExisted(restaurantId));
        return dishRepository.save(dish);
    }
}
