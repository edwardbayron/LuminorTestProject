package com.luminor.luminortestproject.data.repository

import com.luminor.luminortestproject.data.local.SessionManager
import com.luminor.luminortestproject.data.local.UserDao
import com.luminor.luminortestproject.data.local.UserEntity
import com.luminor.luminortestproject.util.hashPassword

class UserRepository(
    private val userDao: UserDao,
    private val sessionManager: SessionManager
) {

    val loggedInEmail = sessionManager.loggedInEmail

    suspend fun register(email: String, password: String): Result<Unit> {
        return try {
            val existing = userDao.getUserByEmail(email)
            if (existing != null) {
                return Result.failure(Exception("User already exists"))
            }
            userDao.insertUser(
                UserEntity(
                    email = email,
                    passwordHash = hashPassword(password)
                )
            )
            sessionManager.saveLogin(email)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(Exception("Server error"))
        }
    }

    suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            val user = userDao.getUserByEmail(email)
            if (user == null) {
                return Result.failure(Exception("User doesn't exist"))
            }
            if (user.passwordHash != hashPassword(password)) {
                return Result.failure(Exception("Wrong password"))
            }
            sessionManager.saveLogin(email)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(Exception("Server error"))
        }
    }

    suspend fun logout() {
        sessionManager.clearLogin()
    }

    suspend fun getUserByEmail(email: String): UserEntity? {
        return userDao.getUserByEmail(email)
    }
}
