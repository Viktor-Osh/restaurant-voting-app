package ru.projects.restaurant_voting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "dish", uniqueConstraints = @UniqueConstraint(columnNames = {"restaurant_id", "name", "menu_date"}))
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dish extends NamedEntity {

    @Column(name = "menu_date")
    @NotNull
    private LocalDate menuDate;

    @Column(name = "price", nullable = false)
    @NotNull
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    private Restaurant restaurant;

    public Dish(Integer id, LocalDate menuDate, Double price, String name, Restaurant restaurant) {
        super(id, name);
        this.menuDate = menuDate;
        this.price = price;
        this.restaurant = restaurant;
    }
}

