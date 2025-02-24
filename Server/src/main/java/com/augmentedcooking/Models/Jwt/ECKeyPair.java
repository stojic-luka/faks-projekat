package com.augmentedcooking.Models.Jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

@Getter
@AllArgsConstructor
@ToString
public final class ECKeyPair implements java.io.Serializable {

    private final ECPublicKey publicKey;
    private final ECPrivateKey privateKey;
}