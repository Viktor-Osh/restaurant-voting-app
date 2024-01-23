package ru.projects.restaurant_voting.web.restaurant;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.projects.restaurant_voting.model.Restaurant;
import ru.projects.restaurant_voting.repository.RestaurantRepository;
import ru.projects.restaurant_voting.util.validation.ValidationUtil;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = ProfileRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestaurantController {

    static final String REST_URL = "/api/admin/restaurants";

    private RestaurantRepository repository;

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("get restaurant with id={}", id);
        return repository.getExisted(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant with id={}", id);
        repository.deleteExisted(id);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("getAll restaurants");
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
            ValidationUtil.checkNew(restaurant);
            Restaurant created = repository.save(restaurant);
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "/{id}")
                    .buildAndExpand(created.getId()).toUri();
            return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update {} with id={}", restaurant, id);
        ValidationUtil.assureIdConsistent(restaurant, id);
        repository.save(restaurant);
    }

    @GetMapping("/{id}/with-dishes")
    public ResponseEntity<Restaurant> getWithDishes(@PathVariable int id) {
        log.info("get restaurant with id={} with dishes", id);
        return ResponseEntity.of(repository.getWithDishes(id));
    }
}
