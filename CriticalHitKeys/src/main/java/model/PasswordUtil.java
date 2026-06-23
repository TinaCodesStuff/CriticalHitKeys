package model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordUtil {
    // Per SHA-256 cambiare questa costante e allargare Password_Ut a VARCHAR(64).
    private static final String HASH_ALGORITHM = "SHA-1";

    private PasswordUtil() {
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return toHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Algoritmo di hash non disponibile", e);
        }
    }

    public static boolean passwordMatches(String password, String savedHash) {
        if (password == null || savedHash == null) {
            return false;
        }

        String candidateHash = hashPassword(password);
        return candidateHash.equalsIgnoreCase(savedHash);
    }

    private static String toHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder(bytes.length * 2);

        for (byte b : bytes) {
            hex.append(String.format("%02x", b));
        }

        return hex.toString();
    }
}
