package ru.projects.restaurant_voting.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.projects.restaurant_voting.model.Vote;

import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    Optional<Vote> findByUserId(int userId);

}
