package ru.projects.restaurant_voting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.projects.restaurant_voting.model.Restaurant;
import ru.projects.restaurant_voting.model.User;
import ru.projects.restaurant_voting.model.Vote;
import ru.projects.restaurant_voting.repository.RestaurantRepository;
import ru.projects.restaurant_voting.repository.UserRepository;
import ru.projects.restaurant_voting.repository.VoteRepository;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class VoteService {
    private final RestaurantRepository restaurantRepository;

    private final UserRepository userRepository;

    private final VoteRepository voteRepository;

    @Transactional
    public Vote save(int userId, int restaurantId, LocalDate localDate) {
        Restaurant restaurant = restaurantRepository.getExisted(restaurantId);
        User user = userRepository.getExisted(userId);
        Vote vote = new Vote(user, restaurant, localDate);
        return voteRepository.save(vote);
    }
}
