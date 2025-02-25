package com.augmentedcooking.Managers.Security;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import java.util.Base64;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.augmentedcooking.Models.Jwt.ECKeyPair;

import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

public class JwtKeyManager {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static ECKeyPair loadAccessKeys(String privateKeyB64, String publicKeyB64) {
        try {
            ECPrivateKey privateKey = loadPrivateKey(Base64.getDecoder().decode(privateKeyB64));
            ECPublicKey publicKey = loadPublicKey(Base64.getDecoder().decode(publicKeyB64));
            return new ECKeyPair(publicKey, privateKey);
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    public static ECKeyPair loadRefreshKeys(String privateKeyB64, String publicKeyB64) {
        try {
            ECPrivateKey privateKey = loadPrivateKey(Base64.getDecoder().decode(privateKeyB64));
            ECPublicKey publicKey = loadPublicKey(Base64.getDecoder().decode(publicKeyB64));
            return new ECKeyPair(publicKey, privateKey);
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    private static ECPrivateKey loadPrivateKey(byte[] keyBytes)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        return (ECPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    private static ECPublicKey loadPublicKey(byte[] keyBytes)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        return (ECPublicKey) keyFactory.generatePublic(keySpec);
    }
}
