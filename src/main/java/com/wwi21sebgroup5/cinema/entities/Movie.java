package com.wwi21sebgroup5.cinema.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
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


}
