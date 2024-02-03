package ru.projects.restaurant_voting.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.projects.restaurant_voting.model.Vote;
import ru.projects.restaurant_voting.repository.VoteRepository;
import ru.projects.restaurant_voting.util.ClockHolder;
import ru.projects.restaurant_voting.web.AbstractControllerTest;

import java.time.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.projects.restaurant_voting.web.user.UserTestData.ADMIN_MAIL;
import static ru.projects.restaurant_voting.web.user.UserTestData.USER_MAIL;
import static ru.projects.restaurant_voting.web.vote.ProfileVoteController.REST_URL;
import static ru.projects.restaurant_voting.web.vote.VoteTestData.*;

class ProfileVoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private VoteRepository repository;

    static void setUpClock(LocalTime time) {
        LocalDate date = LocalDate.now();
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        final Clock fixed = Clock.fixed(dateTime.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
        ClockHolder.setClock(fixed);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(ProfileVoteController.REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(VOTE_MATCHER.contentJson(votesByUser1));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "date/2024-01-01"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(userVotesByDate));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Vote newAdminVote = VoteTestData.getNewAdminVote();
        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URL_SLASH + "restaurant/1"));
        Vote created = VOTE_MATCHER.readFromJson(actions);
        int newId = created.id();
        newAdminVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newAdminVote);
        VOTE_MATCHER.assertMatch(repository.getExisted(newId), newAdminVote);
    }

    //create a duplicate vote on this date for this user
    @Test
    @WithUserDetails(value = USER_MAIL)
    void createDuplicate() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + "restaurant/1"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        setUpClock(LocalTime.of(10, 10));
        Vote updated = VoteTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + "restaurant/2"))
                .andExpect(status().isNoContent());
        VOTE_MATCHER.assertMatch(repository.getExisted(VOTE4_ID), updated);
    }

    //update after deadline
    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateForbidden() throws Exception {
        setUpClock(LocalTime.of(12, 10));
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + "restaurant/2"))
                .andExpect(status().isForbidden());
    }
}
