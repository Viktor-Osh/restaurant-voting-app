package ru.projects.restaurant_voting.web.dish;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping(value = ProfileDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class ProfileDishController {
    static final String REST_URL = "/api/profile/dishes";

    private DishRepository repository;

    private DishService service;

    @GetMapping("/{id}")
    public Dish get(@PathVariable int id) {
        log.info("get dish with id={}", id);
        return repository.getExisted(id);
    }

    @GetMapping
    public List<Dish> getAll() {
        log.info("getAll dishes");
        return repository.findAllByOrderByRestaurant_IdAsc();
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
