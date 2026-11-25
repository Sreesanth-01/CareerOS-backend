package com.project.careerOs.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.project.careerOs.model.User;
import com.project.careerOs.security.JwtUtil;

public class JwtUtilTest {
    private JwtUtil jwtUtil;
    private static final String TEST_SECRET = "aabbccddeeffgghhiijjkkllmmnnooppqqrrssttuuvvwwxxyyzz";

    @BeforeEach 
    void setup(){
        jwtUtil = new JwtUtil(TEST_SECRET);
    }

    @Test 
    void generateToken_shouldReturnValidToken(){
        String email = "test3@gmail.com";
        User user = new User();
        user.setEmail(email);

        String token = jwtUtil.generateToken(user);

        assertNotNull(token);
        assertFalse(token.isBlank());

        assertTrue(jwtUtil.validateToken(token));

    }

    @Test 
    void extractEmail_shouldReturnUserEmail(){
        String email = "test3@gmail.com";

        User user = new User();
        user.setEmail(email);

        String token = jwtUtil.generateToken(user);

        String extractedEmail = jwtUtil.extractEmail(token);

        assertEquals(email, extractedEmail);
    }

    @Test 
    void validateToken_shoudlReturnFalseForInvalidToken(){
        String invalidToken = "invalid.token.jwt";
        assertFalse(jwtUtil.validateToken(invalidToken));

    }
}
