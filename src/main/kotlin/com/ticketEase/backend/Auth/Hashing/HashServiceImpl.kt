package com.ticketEase.backend.Auth.Hashing

import java.security.SecureRandom
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils

class HashServiceImpl : HashService {
    override fun generateSaltedHash(value: String, saltLength: Int): Hash {
        val salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLength)
        val saltAsHex = Hex.encodeHexString(salt)
        val hash = DigestUtils.sha256Hex("$saltAsHex$value")
        return Hash(
            hash = hash,
            secret = saltAsHex
        )
    }

    override fun verify(value: String, saltedHash: Hash): Boolean {
        return DigestUtils.sha256Hex(saltedHash.secret + value) == saltedHash.hash
    }
}