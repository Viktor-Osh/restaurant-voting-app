package ru.projects.restaurant_voting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.projects.restaurant_voting.model.Dish;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {
    @Query("SELECT d from Dish d WHERE d.menuDate = :date ORDER BY d.restaurant.id ASC")
    List<Dish> findAllByMenuDate(LocalDate date);

    @Query("SELECT d FROM Dish d JOIN FETCH d.restaurant " +
            "WHERE d.restaurant.id = :restaurantId and d.menuDate = :date")
    List<Dish> getAllByDateWithRestaurant(LocalDate date, int restaurantId);

    List<Dish> findAllByOrderByRestaurant_IdAsc();
}
