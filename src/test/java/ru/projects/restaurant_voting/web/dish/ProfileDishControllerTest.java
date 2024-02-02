package ru.projects.restaurant_voting.web.dish;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.projects.restaurant_voting.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.projects.restaurant_voting.web.dish.DishTestData.*;
import static ru.projects.restaurant_voting.web.dish.ProfileDishController.REST_URL;
import static ru.projects.restaurant_voting.web.user.UserTestData.NOT_FOUND;
import static ru.projects.restaurant_voting.web.user.UserTestData.USER_MAIL;

class ProfileDishControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = REST_URL + '/';


    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + DISH1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish1));
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
                .andExpect(DISH_MATCHER.contentJson(allDishes));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/date?date=2024-01-01"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dishesByDate));
    }
}
