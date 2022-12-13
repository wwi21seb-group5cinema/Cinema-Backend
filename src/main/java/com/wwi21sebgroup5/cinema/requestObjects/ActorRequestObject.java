package com.wwi21sebgroup5.cinema.requestObjects;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@AllArgsConstructor
@Data
public class ActorRequestObject {

        @NotNull
        @NotEmpty
        private String name;

        @NotNull
        @NotEmpty
        private String firstName;


        @NotNull
        @NotEmpty
        private Date birthdate;

        public ActorRequestObject()
        {
                super();
        }

    }
