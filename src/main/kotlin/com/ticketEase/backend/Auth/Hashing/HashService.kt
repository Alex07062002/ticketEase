package com.ticketEase.backend.Auth.Hashing

interface HashService {
    fun generateSaltedHash(value: String, saltLength: Int = 32): Hash
    fun verify(value: String, saltedHash: Hash): Boolean
}