package com.wwi21sebgroup5.cinema.requestObjects;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TmdbMovieRequestObject {

    private int tmdbMovieId;

    public TmdbMovieRequestObject() {
        super();
    }

}
