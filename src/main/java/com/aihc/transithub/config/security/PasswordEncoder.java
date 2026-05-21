package com.aihc.transithub.config.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Password encoder component using BCrypt for secure password hashing.
 * Encrypts passwords on user creation and validates them during login.
 *
 * @author Alvaro Huanca
 */
@Component
public class PasswordEncoder {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * Encode a plain text password using BCrypt.
     *
     * @param password Plain text password
     * @return Encrypted password hash
     */
    public String encode(String password) {
        return encoder.encode(password);
    }

    /**
     * Validate a plain text password against an encrypted hash.
     *
     * @param plainPassword     Plain text password from user input
     * @param encryptedPassword Encrypted password hash from database
     * @return true if passwords match, false otherwise
     */
    public boolean matches(String plainPassword, String encryptedPassword) {
        return encoder.matches(plainPassword, encryptedPassword);
    }
}

