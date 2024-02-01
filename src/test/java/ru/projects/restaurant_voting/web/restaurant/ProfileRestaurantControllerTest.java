package ru.projects.restaurant_voting.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.projects.restaurant_voting.model.Restaurant;
import ru.projects.restaurant_voting.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.projects.restaurant_voting.web.dish.DishTestData.DISH_MATCHER;
import static ru.projects.restaurant_voting.web.dish.DishTestData.dishesByRestaurant1;
import static ru.projects.restaurant_voting.web.restaurant.ProfileRestaurantController.REST_URL;
import static ru.projects.restaurant_voting.web.restaurant.RestaurantTestData.*;
import static ru.projects.restaurant_voting.web.user.UserTestData.USER_MAIL;

class ProfileRestaurantControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = REST_URL + '/';

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + RESTAURANT1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurant1));
    }
    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurants));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getWithDishes() throws Exception {
        restaurant1.setDishes(dishesByRestaurant1);
        ResultActions actions = perform(MockMvcRequestBuilders.get(REST_URL_SLASH + RESTAURANT1_ID
                + "/with-dishes"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        Restaurant restaurant = RESTAURANT_MATCHER.readFromJson(actions);

        RESTAURANT_MATCHER.assertMatch(restaurant, restaurant1);
        DISH_MATCHER.assertMatch(restaurant.getDishes(), dishesByRestaurant1);
    }

}
