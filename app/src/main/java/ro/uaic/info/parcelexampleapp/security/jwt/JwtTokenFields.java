package ro.uaic.info.parcelexampleapp.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtTokenFields {
    EMAIL("email"),
    FULL_NAME("fullName");

    private String value;
}