package com.ua.schoolboard.rest.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdateStudentTOTest {

    @Test
    void createNew() {
        UpdateStudentTO result = (UpdateStudentTO) Role.STUDENT.newUser();
        assertEquals(0, (int) result.getBalance().getAmount());
        assertEquals(0, (int) result.getBalance().getClassesPaid());
        assertNotNull(result.getLanguages());
        assertNotNull(result.getRates());
    }
}