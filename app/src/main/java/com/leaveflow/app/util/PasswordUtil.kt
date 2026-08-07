package com.leaveflow.app.util

import at.favre.lib.crypto.bcrypt.BCrypt

object PasswordUtil {
    private const val COST = 12  // BCrypt work factor

    /**
     * Hashes a plaintext password using BCrypt.
     */
    fun hashPassword(password: String): String =
        BCrypt.withDefaults().hashToString(COST, password.toCharArray())

    /**
     * Verifies a plaintext password against a BCrypt hash.
     */
    fun verifyPassword(password: String, hash: String): Boolean =
        BCrypt.verifyer().verify(password.toCharArray(), hash).verified
}
