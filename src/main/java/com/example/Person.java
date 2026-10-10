package com.example;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Person {
    private String firstName;
    private String lastName;
    private LocalDate birthday;

    Person(String givenName, String surname, LocalDate birthday) {
        this.firstName = givenName;
        this.lastName = surname;
        this.birthday = birthday;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    double getAge() {
        return ChronoUnit.YEARS.between(birthday, LocalDate.now());
    }
}