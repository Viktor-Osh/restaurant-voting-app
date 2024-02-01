    package ru.projects.restaurant_voting.web.dish;

    import lombok.AllArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.cache.annotation.Cacheable;
    import org.springframework.format.annotation.DateTimeFormat;
    import org.springframework.http.MediaType;
    import org.springframework.web.bind.annotation.*;
    import ru.projects.restaurant_voting.model.Dish;
    import ru.projects.restaurant_voting.repository.DishRepository;

    import java.time.LocalDate;
    import java.util.List;

    import static ru.projects.restaurant_voting.web.dish.AdminDishController.DISH_CACHE;

    @RestController
    @AllArgsConstructor
    @RequestMapping(value = ProfileDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    @Slf4j
    public class ProfileDishController {
        static final String REST_URL = "/api/profile/dishes";

        private DishRepository repository;

        @GetMapping("/{id}")
        public Dish get(@PathVariable int id) {
            log.info("get dish with id={}", id);
            return repository.getExisted(id);
        }

        @GetMapping
        public List<Dish> getAll() {
            log.info("getAll dishes");
            return repository.findAllByOrderByRestaurant_IdAscIdAsc();
        }

        @Cacheable(value = DISH_CACHE, key = "#date")
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
