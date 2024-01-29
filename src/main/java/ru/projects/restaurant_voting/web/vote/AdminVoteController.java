package ru.projects.restaurant_voting.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.projects.restaurant_voting.model.Vote;
import ru.projects.restaurant_voting.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteController {
    static final String REST_URL = "/api/admin/votes";

    private VoteRepository repository;

    @GetMapping
    public List<Vote> getAll() {
        log.info("getAll votes");
        return repository.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<Vote> getByUserId(@PathVariable int userId) {
        log.info("get user id={} votes", userId);
        return repository.findAllByUserId(userId);
    }

    @GetMapping("/date/{date}")
    public List<Vote> getAllByDate(@PathVariable LocalDate date) {
        return repository.getAllByDate(date);
    }
}
