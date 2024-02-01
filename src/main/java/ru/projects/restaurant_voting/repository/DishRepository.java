package ru.projects.restaurant_voting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.projects.restaurant_voting.model.Dish;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {
    @Query("SELECT d from Dish d WHERE d.menuDate = :date ORDER BY d.restaurant.id ASC, d.id ASC")
    List<Dish> findAllByMenuDate(LocalDate date);

    @Query("SELECT d FROM Dish d JOIN FETCH d.restaurant " +
            "WHERE d.restaurant.id = :restaurantId and d.menuDate = :date")
    List<Dish> getAllByDateWithRestaurant(LocalDate date, int restaurantId);

    List<Dish> findAllByOrderByRestaurant_IdAscIdAsc();

    @Query("SELECT d FROM Dish d WHERE d.id =:id AND d.restaurant.id = :restaurantId")
    Optional<Dish> getBelonged(int restaurantId, int id);
}
