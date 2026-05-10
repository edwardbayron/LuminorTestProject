package com.luminor.luminortestproject.data.repository

import com.luminor.luminortestproject.data.local.SessionManager
import com.luminor.luminortestproject.data.local.UserDao
import com.luminor.luminortestproject.data.local.UserEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UserRepositoryTest {

    private lateinit var userDao: UserDao
    private lateinit var sessionManager: SessionManager
    private lateinit var repository: UserRepository

    @Before
    fun setup() {
        userDao = mockk(relaxed = true)
        sessionManager = mockk(relaxed = true)
        repository = UserRepository(userDao, sessionManager)
    }

    @Test
    fun `login fail if user not exist`() = runTest {
        coEvery { userDao.getUserByEmail("test@test.com") } returns null

        val result = repository.login("test@test.com", "password123")
        assertTrue(result.isFailure)
        assertEquals("User doesn't exist", result.exceptionOrNull()?.message)
    }

    @Test
    fun `login fail if password is wrong`() = runTest {
        coEvery { userDao.getUserByEmail("test@test.com") } returns UserEntity(
            email = "test@test.com",
            passwordHash = com.luminor.luminortestproject.util.hashPassword("correctpass")
        )

        val result = repository.login("test@test.com", "wrongpass")
        assertTrue(result.isFailure)
        assertEquals("Wrong password", result.exceptionOrNull()?.message)
    }

    @Test
    fun `register fail if user already exist`() = runTest {
        coEvery { userDao.getUserByEmail("test@test.com") } returns UserEntity(
            email = "test@test.com",
            passwordHash = "hash"
        )

        val result = repository.register("test@test.com", "password123")
        assertTrue(result.isFailure)
        assertEquals("User already exists", result.exceptionOrNull()?.message)
    }

    @Test
    fun `register success for new user`() = runTest {
        coEvery { userDao.getUserByEmail("new@test.com") } returns null

        val result = repository.register("new@test.com", "password123")
        assertTrue(result.isSuccess)
        coVerify { sessionManager.saveLogin("new@test.com") }
    }

    @Test
    fun `login success with correct password`() = runTest {
        coEvery { userDao.getUserByEmail("test@test.com") } returns UserEntity(
            email = "test@test.com",
            passwordHash = com.luminor.luminortestproject.util.hashPassword("password123")
        )

        val result = repository.login("test@test.com", "password123")
        assertTrue(result.isSuccess)
        coVerify { sessionManager.saveLogin("test@test.com") }
    }
}
