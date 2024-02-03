package ru.projects.restaurant_voting.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.projects.restaurant_voting.error.LateVoteException;
import ru.projects.restaurant_voting.error.NotFoundException;
import ru.projects.restaurant_voting.model.Vote;
import ru.projects.restaurant_voting.repository.VoteRepository;
import ru.projects.restaurant_voting.service.VoteService;
import ru.projects.restaurant_voting.util.ClockHolder;
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
@RequestMapping(value = ProfileVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileVoteController {
    static final String REST_URL = "/api/profile/votes";

    static final LocalTime deadLine = LocalTime.of(11, 0);

    private VoteRepository repository;

    private VoteService service;

    @GetMapping()
    public List<Vote> getAll(@AuthenticationPrincipal AuthUser user) {
        log.info("get user id={} votes", user.id());
        return repository.findAllByUserId(user.id());
    }

    @GetMapping("/date/{date}")
    public Vote getByDate(@AuthenticationPrincipal AuthUser user, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return repository.getByDate(user.id(), date).orElseThrow(() -> new NotFoundException("User id=" + user.id() + " has no vote for the date " + date));
    }

    @PostMapping(value = "/restaurant/{restaurantId}")
    public ResponseEntity<Vote> create(@AuthenticationPrincipal AuthUser user, @PathVariable int restaurantId) {
        log.info("User with id={} create vote for restaurant {}", user.id(), restaurantId);
        LocalDate now = LocalDate.now(ClockHolder.getClock());
        if (service.voteExist(user.id(), now)) {
            throw new LateVoteException("User with id =" + user.id() + " has already voted today");
        }
        Vote created = service.save(user.id(), restaurantId, now);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/restaurant/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser user, @PathVariable int restaurantId) {
        LocalDateTime now = LocalDateTime.now(ClockHolder.getClock());
        Vote vote = getByDate(user, now.toLocalDate());
        if (now.toLocalTime().isBefore(deadLine)) {
            service.update(vote, restaurantId);
        } else {
            throw new LateVoteException("You can't change your vote after: " + deadLine + ", now: "
                    + now.toLocalTime().truncatedTo(ChronoUnit.MINUTES));
        }
    }
}