package ru.projects.restaurant_voting.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = ProfileVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileVoteController {
    static final String REST_URL = "/api/profile/votes";

    static final LocalTime deadLine = LocalTime.of(11, 0);

    private VoteRepository repository;

    private VoteService service;

    @GetMapping()
    public Vote getByUserId(@AuthenticationPrincipal AuthUser user) {
        log.info("get user id={} votes", user.id());
        return repository.findByUserId(user.id()).orElseThrow(() -> new VoteNotFoundException("User id=" + user.id() + " doesn't have votes"));
    }

    @DeleteMapping("/{id}")
    public void delete(@AuthenticationPrincipal AuthUser user, @PathVariable int id) {
        log.info("delete vote for date: {}", id);
        Vote vote = repository.getBelonged(user.id(), id);
        repository.delete(vote);
    }

    @PostMapping("/restaurant/{restaurantId}")
    public ResponseEntity<Vote> create(@AuthenticationPrincipal AuthUser user, @PathVariable int restaurantId) {
        log.info("create vote for restaurant {}", restaurantId);
        LocalDateTime now = LocalDateTime.now();
        if (service.voteExist(user.id(), now.toLocalDate())) {
            throw new LateVoteException("User with id =" + user.id() + " has already voted today");
        }
        Vote created = service.save(user.id(), restaurantId, now.toLocalDate());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "/{id}")
                    .buildAndExpand(created.getId()).toUri();
            return ResponseEntity.created(uriOfNewResource).body(created);
    }

            @PutMapping("/restaurant/{restaurantId}")
        public void update(@AuthenticationPrincipal AuthUser user, @PathVariable int restaurantId) {
            LocalDateTime now = LocalDateTime.now();
            getByUserId(user);
            if (now.toLocalTime().isBefore(deadLine)) {
                service.save(user.id(), restaurantId, now.toLocalDate());
            } else {
                throw new LateVoteException("You can't change vote before: " + deadLine + ", now: "
                        + now.toLocalTime().truncatedTo(ChronoUnit.MINUTES));
            }
        }
}
