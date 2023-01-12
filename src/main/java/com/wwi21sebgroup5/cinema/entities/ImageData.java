package com.wwi21sebgroup5.cinema.entities;

import com.wwi21sebgroup5.cinema.helper.ImageCompressor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Objects;
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

    @NotNull
    @Column
    private boolean compressed;

    @Lob
    @Column(length = ImageCompressor.MAX_IMAGE_SIZE_IN_BYTE)
    private byte[] imageData;

    public ImageData(String type, byte[] imageData, boolean compressed) {
        this.type = type;
        this.imageData = imageData;
        this.compressed = compressed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageData imageData1 = (ImageData) o;

        if (compressed != imageData1.compressed) return false;
        if (!Objects.equals(id, imageData1.id)) return false;
        if (!Objects.equals(type, imageData1.type)) return false;
        return Arrays.equals(imageData, imageData1.imageData);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (compressed ? 1 : 0);
        result = 31 * result + Arrays.hashCode(imageData);
        return result;
    }
}
