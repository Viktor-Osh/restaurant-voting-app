package ru.projects.restaurant_voting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "vote", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "menu_date"}))
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(name = "user_id", updatable = false, insertable = false)
    private int userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    private Restaurant restaurant;

    @Column(name = "restaurant_id", updatable = false, insertable = false)
    private int restaurantId;

    @Column(name = "menu_date")
    @NotNull
    private LocalDate menuDate;

    public Vote(User user, Restaurant restaurant, LocalDate menuDate) {
        super();
        this.user = user;
        this.userId = user.id;
        this.restaurantId = restaurant.id();
        this.restaurant = restaurant;
        this.menuDate = menuDate;
    }
}
