package com.wwi21sebgroup5.cinema.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;


@Getter
@Setter
@ToString
@Entity
@Table(name = "imageData")
@NoArgsConstructor
public class ImageData {

    @Id
    @Column
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Column
    private String type;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] imageData;

    public ImageData(String type, byte[] imageData) {
        this.type = type;
        this.imageData = imageData;
    }
}
