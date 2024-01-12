package ru.projects.restaurant_voting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.projects.restaurant_voting.model.Restaurant;

import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("SELECT r from Restaurant r JOIN FETCH r.dishes WHERE r.id = :id")
    Optional<Restaurant> getWithDishes(int id);
}
