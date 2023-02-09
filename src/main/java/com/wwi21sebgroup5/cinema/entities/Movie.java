package com.wwi21sebgroup5.cinema.entities;

import com.wwi21sebgroup5.cinema.enums.FSK;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
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
    @ManyToOne(fetch = FetchType.LAZY)
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

    @OneToOne
    @JoinColumn(name = "Image_id", referencedColumnName = "id")
    private ImageData image;

    @Column
    private String image_url;

    @Column
    private Boolean externalImage;

    @Column
    private String trailer_url;

    @Column
    private Float rating;

    @NotNull
    @Column
    private String name;

    @NotNull
    @Column
    @Length(max = 1000)
    private String description;

    @NotNull
    @Column
    private Integer length;

    @NotNull
    @Column
    private LocalDate start_date;

    @Column
    private LocalDate end_date;

    /**
     * This constructor is suited for the usecase of adding a custom movie into the db
     *
     * @param producer    Producer of the movie
     * @param director    Director of the movie
     * @param fsk         FSK of the movie
     * @param genre       Genre of the movie
     * @param image       Image of the movie
     * @param name        Title of the movie
     * @param description Description of the movie
     * @param start_date  Start date of the movie in the cinemas
     * @param end_date    End date of the movie in the cinemas
     */
    public Movie(Producer producer,
                 Director director,
                 FSK fsk,
                 Genre genre,
                 ImageData image,
                 String name,
                 String description,
                 Float rating,
                 Integer length,
                 LocalDate start_date,
                 LocalDate end_date) {
        this.producer = producer;
        this.director = director;
        this.fsk = fsk;
        this.genre = genre;
        this.image = image;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.length = length;
        this.start_date = start_date;
        this.end_date = end_date;
        this.externalImage = false;
    }

    /**
     * this constructor is suited for adding tmdb movies into the db
     *
     * @param producer    Producer of the movie
     * @param director    Director of the movie
     * @param fsk         FSK of the movie
     * @param genre       Genre of the movie
     * @param image_url   URL to the image of the movie
     * @param trailer_url URL to the trailer of the movie
     * @param rating      Rating of the movie
     * @param name        Title of the movie
     * @param description Description of the movie
     * @param length      Length of the movie
     * @param start_date  Start date of the movie in the cinemas
     * @param end_date    End date of the movie in the cinemas
     */
    public Movie(Producer producer,
                 Director director,
                 FSK fsk, Genre genre,
                 String image_url,
                 String trailer_url,
                 float rating,
                 String name,
                 String description,
                 int length,
                 LocalDate start_date,
                 LocalDate end_date) {
        this.producer = producer;
        this.director = director;
        this.fsk = fsk;
        this.genre = genre;
        this.image_url = image_url;
        this.trailer_url = trailer_url;
        this.rating = rating;
        this.name = name;
        this.description = description;
        this.length = length;
        this.start_date = start_date;
        this.end_date = end_date;
        this.externalImage = true;
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
