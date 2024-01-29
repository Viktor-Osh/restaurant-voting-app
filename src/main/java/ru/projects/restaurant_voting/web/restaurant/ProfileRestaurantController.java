package ru.projects.restaurant_voting.web.restaurant;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.projects.restaurant_voting.model.Restaurant;
import ru.projects.restaurant_voting.repository.RestaurantRepository;

import java.util.List;

import static ru.projects.restaurant_voting.web.restaurant.AdminRestaurantController.RESTAURANT_CACHE;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = ProfileRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestaurantController {

    static final String REST_URL = "/api/profile/restaurants";

    private RestaurantRepository repository;

    @Cacheable(value = RESTAURANT_CACHE, key = "#id")
    @GetMapping("/{id}")
    public  ResponseEntity<Restaurant> get(@PathVariable int id) {
        log.info("get restaurant with id={}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("getAll restaurants");
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @GetMapping("/{id}/with-dishes")
    public ResponseEntity<Restaurant> getWithDishes(@PathVariable int id) {
        log.info("get restaurant with id={} with dishes", id);
        return ResponseEntity.of(repository.getWithDishes(id));
    }
}
