package ru.projects.restaurant_voting.web.vote;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.projects.restaurant_voting.error.VoteNotFoundException;
import ru.projects.restaurant_voting.model.Vote;
import ru.projects.restaurant_voting.repository.VoteRepository;
import ru.projects.restaurant_voting.service.VoteService;
import ru.projects.restaurant_voting.web.AuthUser;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    static final String REST_URL = "/api/votes";

    static final LocalTime deadLine = LocalTime.of(11, 0, 0);

    private VoteRepository repository;

    private VoteService service;

    @GetMapping
    public List<Vote> getAll() {
        log.info("getAll votes");
        return repository.findAll();
    }

    @GetMapping("/{userId}")
    public Vote getByUserId(@PathVariable int userId) {
        log.info("get user id={} votes", userId);
        return repository.findByUserId(userId).orElseThrow(() -> new VoteNotFoundException("User id=" + userId + " doesn't have votes"));
    }

    @PostMapping("/{restaurantId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Vote> create(@AuthenticationPrincipal AuthUser user, @PathVariable int restaurantId) {
        log.info("create vote for restaurant {}", restaurantId);
        Vote created = service.save(user.id(), restaurantId, LocalDate.now());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}