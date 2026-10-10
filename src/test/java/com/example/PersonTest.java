package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    private Person testPerson;

    @BeforeEach
    public void setup(){
        // Fictional identity and synthetic birthday for public test data.
        testPerson = new Person("Alex", "Example", LocalDate.now().minusYears(30));
    }

    @Test
    public void testGetFullName(){
        String expectedFullName = "Alex Example";
        assertEquals(expectedFullName, testPerson.getFullName());
    }

    @Test
    public void testGetAge(){
        double age = 30;
        assertEquals(age, testPerson.getAge());
    }

}
