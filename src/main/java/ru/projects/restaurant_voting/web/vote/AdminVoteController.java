package ru.projects.restaurant_voting.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.projects.restaurant_voting.error.LateVoteException;
import ru.projects.restaurant_voting.error.VoteNotFoundException;
import ru.projects.restaurant_voting.model.Vote;
import ru.projects.restaurant_voting.repository.VoteRepository;
import ru.projects.restaurant_voting.service.VoteService;
import ru.projects.restaurant_voting.web.AuthUser;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
    public Vote getByUserId(@PathVariable int userId) {
        log.info("get user id={} votes", userId);
        return repository.findByUserId(userId).orElseThrow(() -> new VoteNotFoundException("User id=" + userId + " doesn't have votes"));
    }

    @GetMapping("/date/{date}")
    public List<Vote> getAllByDate(@RequestParam LocalDate date) {
        return repository.getAllByDate(date);
    }
}
