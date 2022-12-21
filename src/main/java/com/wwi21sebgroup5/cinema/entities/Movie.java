package com.wwi21sebgroup5.cinema.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @Column
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Producer_id", referencedColumnName = "id")
    @ToString.Exclude
    private Producer producer;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "Director_id", referencedColumnName = "id")
    @ToString.Exclude
    private Director director;

    @Enumerated(EnumType.STRING)
    private FSK fsk;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "Genre_id", referencedColumnName = "id")
    private Genre genre;

    @NotNull
    @OneToOne
    @JoinColumn(name = "Image_id", referencedColumnName = "id")
    private ImageData image;

    @NotNull
    @Column
    private String name;

    @NotNull
    @Column
    private String description;

    @NotNull
    @Column
    private Date start_date;

    @Column
    private Date end_date;

    public Movie(Producer producer,
                 Director director,
                 FSK fsk,
                 Genre genre,
                 ImageData image,
                 String name,
                 String description,
                 Date start_date,
                 Date end_date) {
        this.producer = producer;
        this.director = director;
        this.fsk = fsk;
        this.genre = genre;
        this.image = image;
        this.name = name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (!Objects.equals(id, movie.id)) return false;
        if (!Objects.equals(producer, movie.producer)) return false;
        if (!Objects.equals(director, movie.director)) return false;
        if (fsk != movie.fsk) return false;
        if (!Objects.equals(genre, movie.genre)) return false;
        if (!Objects.equals(image, movie.image)) return false;
        if (!Objects.equals(name, movie.name)) return false;
        if (!Objects.equals(description, movie.description)) return false;
        if (!Objects.equals(start_date, movie.start_date)) return false;
        return Objects.equals(end_date, movie.end_date);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (producer != null ? producer.hashCode() : 0);
        result = 31 * result + (director != null ? director.hashCode() : 0);
        result = 31 * result + (fsk != null ? fsk.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (start_date != null ? start_date.hashCode() : 0);
        result = 31 * result + (end_date != null ? end_date.hashCode() : 0);
        return result;
    }
}
