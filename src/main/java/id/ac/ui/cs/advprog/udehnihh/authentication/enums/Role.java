package id.ac.ui.cs.advprog.udehnihh.authentication.enums;

import lombok.Getter;

@Getter
public enum Role {
    STAFF("STAFF"),
    TUTOR("TUTOR"),
    STUDENT("STUDENT");

    private final String value;

    Role(String value) {
        this.value = value;
    }
}