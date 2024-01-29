package ru.projects.restaurant_voting.web.dish;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.projects.restaurant_voting.error.NotFoundException;
import ru.projects.restaurant_voting.model.Dish;
import ru.projects.restaurant_voting.repository.DishRepository;
import ru.projects.restaurant_voting.service.DishService;
import ru.projects.restaurant_voting.util.validation.ValidationUtil;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminDishController {
    static final String REST_URL = "/api/admin/dishes";

    static final String DISH_CACHE = "dishes";

    private DishRepository repository;

    private DishService service;

    @CacheEvict(value = DISH_CACHE, allEntries = true)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete dish with id={}", id);
        repository.deleteExisted(id);
    }

    @CacheEvict(value = DISH_CACHE, key = "#dish.menuDate")
    @PostMapping(value = "restaurant/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@PathVariable int restaurantId, @Valid @RequestBody Dish dish) {
        log.info("create {} for restaurant {}", dish, restaurantId);
        ValidationUtil.checkNew(dish);
        Dish created = service.save(restaurantId, dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @CacheEvict(value = DISH_CACHE, key = "#dish.menuDate")
    @PutMapping(value = "/{id}/restaurant/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int id, @PathVariable int restaurantId) {
        log.info("update {} with id={}", dish, id);
        ValidationUtil.assureIdConsistent(dish, id);
        repository.getBelonged(restaurantId, id).orElseThrow(
                () -> new NotFoundException("Dish with id=" + id + " is not exist or not belong to restaurant id= " + restaurantId));
        service.save(restaurantId, dish);
    }
}
