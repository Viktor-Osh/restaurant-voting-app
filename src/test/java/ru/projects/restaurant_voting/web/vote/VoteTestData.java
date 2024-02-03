package ru.projects.restaurant_voting.web.vote;

import ru.projects.restaurant_voting.model.Vote;
import ru.projects.restaurant_voting.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

import static ru.projects.restaurant_voting.web.restaurant.RestaurantTestData.restaurant1;
import static ru.projects.restaurant_voting.web.restaurant.RestaurantTestData.restaurant2;
import static ru.projects.restaurant_voting.web.user.UserTestData.*;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class,"user", "restaurant");

    public static final int VOTE1_ID = 1;
    public static final int VOTE2_ID = 2;
    public static final int VOTE3_ID = 3;
    public static final int VOTE4_ID = 4;

    public static final Vote vote1 = new Vote(VOTE1_ID, user, restaurant2, LocalDate.of(2024, 1, 1));
    public static final Vote vote2 = new Vote(VOTE2_ID, admin, restaurant1, LocalDate.of(2024, 1, 1));
    public static final Vote vote3 = new Vote(VOTE3_ID, guest, restaurant1, LocalDate.of(2024, 1, 1));
    public static final Vote vote4 = new Vote(VOTE4_ID, user, restaurant1, LocalDate.now());

    public static final List<Vote> allVotes = List.of(vote1, vote2, vote3, vote4);
    public static final List<Vote> votesByDate = List.of(vote1, vote2, vote3);
    public static final List<Vote> userVotesByDate = List.of(vote1);
    public static final List<Vote> votesByUser1 = List.of(vote1, vote4);

    public static Vote getNewAdminVote() {
        return new Vote(null, admin, restaurant1, LocalDate.now());
    }

    public static Vote getUpdated() {
        return new Vote(VOTE4_ID, user, restaurant2, LocalDate.now());
    }

}
