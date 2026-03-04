package org.kamil.librarymanager.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256PasswordHasher implements PasswordHasher{

    private static final MessageDigest DIGEST;

    static {
        try {
            DIGEST = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new ExceptionInInitializerError("SHA-256 algorithm not found");
        }
    }

    @Override
    public String hash(String password) {
        if (password == null) return null;

        byte[] hash = DIGEST.digest(password.getBytes());
        return bytesToHex(hash);
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
