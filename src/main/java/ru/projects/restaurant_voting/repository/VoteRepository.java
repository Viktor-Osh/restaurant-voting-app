package ru.projects.restaurant_voting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.projects.restaurant_voting.error.VoteNotFoundException;
import ru.projects.restaurant_voting.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    Optional<Vote> findByUserId(int userId);

    @Query("SELECT v FROM Vote v WHERE v.id = :id AND v.user.id = :userId")
    Optional<Vote> get(int userId, int id);

    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId AND v.menuDate = :date ")
    Optional<Vote> getByDate(int userId, LocalDate date);

    default Vote getBelonged(int userId, int id) {
        return get(userId, id).orElseThrow(
                () -> new VoteNotFoundException("Vote id=" + id + "   is not exist or doesn't belong to User id=" + userId));
    }

    @Query("SELECT v FROM Vote v WHERE v.menuDate=:date")
    List<Vote> getAllByDate(LocalDate date);
}
