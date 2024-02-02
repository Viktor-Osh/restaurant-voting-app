package ru.projects.restaurant_voting.web.dish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.projects.restaurant_voting.error.NotFoundException;
import ru.projects.restaurant_voting.model.Dish;
import ru.projects.restaurant_voting.repository.DishRepository;
import ru.projects.restaurant_voting.util.JsonUtil;
import ru.projects.restaurant_voting.web.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.projects.restaurant_voting.web.dish.AdminDishController.REST_URL;
import static ru.projects.restaurant_voting.web.dish.DishTestData.*;
import static ru.projects.restaurant_voting.web.restaurant.RestaurantTestData.restaurant1;
import static ru.projects.restaurant_voting.web.user.UserTestData.ADMIN_MAIL;
import static ru.projects.restaurant_voting.web.user.UserTestData.USER_MAIL;

class AdminDishControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private DishRepository repository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + DISH1_ID))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> repository.getExisted(DISH1_ID));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteAccessConflict() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + DISH1_ID))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Dish newDish = getNew();
        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URL_SLASH + "restaurant/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)));
        Dish created = DISH_MATCHER.readFromJson(actions);
        int newId = created.id();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(repository.getExisted(newId), newDish);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Dish updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + DISH1_ID + "/restaurant/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        DISH_MATCHER.assertMatch(repository.getExisted(DISH1_ID), updated);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Dish invalid = new Dish(null, null, null, dish1.getName(), null);
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + "restaurant/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createDuplicate() throws Exception {
        Dish invalid = new Dish(null, dish2.getMenuDate(), dish2.getPrice(), dish2.getName(), restaurant1);
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + "restaurant/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateHtmlUnsafe() throws Exception {
        Dish invalid = new Dish(DISH1_ID, dish1.getMenuDate(), dish1.getPrice(), "<script>alert(123)</script>", restaurant1);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + DISH1_ID + "/restaurant/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}