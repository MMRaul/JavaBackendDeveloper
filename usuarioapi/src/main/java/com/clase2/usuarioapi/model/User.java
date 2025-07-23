package com.clase2.usuarioapi.model;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private String name;

    @Nonnull
    private String email;

    private Integer age;
    private String occupation;
}
