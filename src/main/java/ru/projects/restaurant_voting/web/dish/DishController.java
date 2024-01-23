package ru.projects.restaurant_voting.web.dish;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.projects.restaurant_voting.error.DataConflictException;
import ru.projects.restaurant_voting.model.Dish;
import ru.projects.restaurant_voting.repository.DishRepository;
import ru.projects.restaurant_voting.service.DishService;
import ru.projects.restaurant_voting.util.validation.ValidationUtil;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@AllArgsConstructor
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class DishController {
    static final String REST_URL = "/api/dishes";

    private DishRepository repository;

    private DishService service;

    @GetMapping("/{id}")
    public Dish get(@PathVariable int id) {
        log.info("get dish with id={}", id);
        return repository.getExisted(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete dish with id={}", id);
        repository.deleteExisted(id);
    }

    @GetMapping
    public List<Dish> getAll() {
        log.info("getAll dishes");
        return repository.findAllByOrderByRestaurant_IdAsc();
    }

    @PostMapping(value = "restaurant/{restaurantId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Dish> create(@PathVariable int restaurantId, @Valid @RequestBody Dish dish) {
        log.info("create {} for restaurant {}", dish, restaurantId);
        ValidationUtil.checkNew(dish);
        Dish created = service.save(restaurantId, dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}/restaurant/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void update(@Valid @RequestBody Dish dish, @PathVariable int id, @PathVariable int restaurantId) {
        log.info("update {} with id={}", dish, id);
        ValidationUtil.assureIdConsistent(dish, id);
        repository.getBelonged(restaurantId, id).orElseThrow(
                        () -> new DataConflictException("Dish with id=" + id +" is not exist or not belong to restaurant id= " + restaurantId));
        service.save(restaurantId, dish);
    }

    @GetMapping("/date")
    public List<Dish> getByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get dishes by date : {}", date);
       return repository.findAllByMenuDate(date);
    }

    @GetMapping("/restaurant/{restaurantId}/date")
    public List<Dish> getByRestaurantAndDate(@PathVariable int restaurantId,
                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get dishes by date : {} with restaurant id={}", date, restaurantId);
        return repository.getAllByDateWithRestaurant(date, restaurantId);
    }

}
