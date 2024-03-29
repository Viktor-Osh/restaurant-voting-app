package ru.projects.restaurant_voting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Restaurant restaurant;

    @Column(name = "restaurant_id", updatable = false, insertable = false)
    private int restaurantId;

    @Column(name = "menu_date")
    @NotNull
    private LocalDate menuDate;

    public Vote(Integer id, User user, Restaurant restaurant, LocalDate menuDate) {
        super(id);
        this.user = user;
        if (user != null) {
            this.userId = user.id;
        }
        this.restaurant = restaurant;
        if (restaurant != null) {
            this.restaurantId = restaurant.id();
        }
        this.menuDate = menuDate;
    }
}
