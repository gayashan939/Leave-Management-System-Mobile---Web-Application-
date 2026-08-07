package com.leaveflow.app.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.leaveflow.app.data.local.dao.UserDao
import com.leaveflow.app.data.local.entity.UserEntity
import com.leaveflow.app.domain.model.Result
import com.leaveflow.app.domain.model.User
import com.leaveflow.app.util.Constants
import com.leaveflow.app.util.PasswordUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore(name = "session_prefs")

@Singleton
class AuthRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userDao: UserDao
) {

    // ── DataStore preference keys ─────────────────────────────────────────────
    private val keyLoggedIn  = booleanPreferencesKey(Constants.PREF_LOGGED_IN)
    private val keyUserId    = stringPreferencesKey(Constants.PREF_USER_ID)
    private val keyUserName  = stringPreferencesKey(Constants.PREF_USER_NAME)
    private val keyUserRole  = stringPreferencesKey(Constants.PREF_USER_ROLE)
    private val keyUserEmail = stringPreferencesKey(Constants.PREF_USER_EMAIL)
    private val keyUserDept  = stringPreferencesKey(Constants.PREF_USER_DEPT)
    private val keyEmpId     = stringPreferencesKey(Constants.PREF_EMP_ID)

    // ── Session state (observed by AuthViewModel) ─────────────────────────────
    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[keyLoggedIn] ?: false
    }

    val currentUser: Flow<User?> = context.dataStore.data.map { prefs ->
        if (prefs[keyLoggedIn] == true) {
            User(
                id         = prefs[keyUserId]    ?: "",
                name       = prefs[keyUserName]  ?: "",
                email      = prefs[keyUserEmail] ?: "",
                role       = prefs[keyUserRole]  ?: "",
                department = prefs[keyUserDept]  ?: "",
                employeeId = prefs[keyEmpId]     ?: ""
            )
        } else null
    }

    // ── Login ─────────────────────────────────────────────────────────────────
    suspend fun login(email: String, password: String): Result<User> {
        if (email.isBlank() || password.isBlank()) {
            return Result.Error("Email and password are required.")
        }

        val entity = userDao.getUserByEmail(email.trim().lowercase())
            ?: return Result.Error("No account found with this email address.")

        if (!PasswordUtil.verifyPassword(password, entity.passwordHash)) {
            return Result.Error("Incorrect password. Please try again.")
        }

        val user = entity.toDomain()
        persistSession(user)
        return Result.Success(user)
    }

    // ── Logout ────────────────────────────────────────────────────────────────
    suspend fun logout() {
        context.dataStore.edit { prefs -> prefs.clear() }
    }

    // ── Session persistence ────────────────────────────────────────────────────
    private suspend fun persistSession(user: User) {
        context.dataStore.edit { prefs ->
            prefs[keyLoggedIn]  = true
            prefs[keyUserId]    = user.id
            prefs[keyUserName]  = user.name
            prefs[keyUserRole]  = user.role
            prefs[keyUserEmail] = user.email
            prefs[keyUserDept]  = user.department
            prefs[keyEmpId]     = user.employeeId
        }
    }

    /** Reads the currently persisted user once (suspend, not Flow). */
    suspend fun getCurrentUserOnce(): User? = currentUser.firstOrNull()
}

private fun UserEntity.toDomain() = User(
    id         = id,
    name       = name,
    email      = email,
    role       = role,
    department = department,
    employeeId = employeeId,
    managerId  = managerId
)
