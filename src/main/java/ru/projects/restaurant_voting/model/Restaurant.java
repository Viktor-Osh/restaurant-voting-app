    package ru.projects.restaurant_voting.model;

    import io.swagger.v3.oas.annotations.media.Schema;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.Size;
    import lombok.AccessLevel;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import org.hibernate.annotations.OnDelete;
    import org.hibernate.annotations.OnDeleteAction;
    import ru.projects.restaurant_voting.util.validation.NoHtml;

    import java.util.List;

    @Entity
    @Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "address"}, name = "unique_restaurant_name_with_address")})
    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public class Restaurant extends NamedEntity {

        @Column(name = "address", nullable = false)
        @NotBlank
        @Size(min = 2, max = 128)
        @NoHtml
        private String address;

        @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
        @OnDelete(action = OnDeleteAction.CASCADE)
        @Schema(hidden = true)
        private List<Dish> dishes;

        public Restaurant(Integer id, String name, String address) {
            super(id, name);
            this.address = address;
        }
    }
