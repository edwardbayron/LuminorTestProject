package com.luminor.luminortestproject.util

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidationTest {

    @Test
    fun `valid email returns true`() {
        assertTrue(isValidEmail("test@example.com"))
    }

    @Test
    fun `invalid email returns false`() {
        assertFalse(isValidEmail("notanemail"))
    }

    @Test
    fun `empty email returns false`() {
        assertFalse(isValidEmail(""))
    }

    @Test
    fun `password with 6 or more chars is valid`() {
        assertTrue(isValidPassword("123456"))
    }

    @Test
    fun `password with less than 6 chars is invalid`() {
        assertFalse(isValidPassword("12345"))
    }

    @Test
    fun `empty password is invalid`() {
        assertFalse(isValidPassword(""))
    }
}
