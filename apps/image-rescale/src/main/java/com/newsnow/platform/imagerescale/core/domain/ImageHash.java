package com.newsnow.platform.imagerescale.core.domain;

import com.newsnow.platform.imagerescale.core.ImageHashingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public record ImageHash(String value) {

    public static ImageHash from(byte[] content) {
        var hash = generateHashFor(content);
        return new ImageHash(hash);
    }

    private static String generateHashFor(byte[] content) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            var hashContent = messageDigest.digest(content);
            return HexFormat.of().formatHex(hashContent);
        } catch (NoSuchAlgorithmException e) {
            throw new ImageHashingException("Cannot obtain the hash from the original image, MD5 algorithm is unknown", e);
        }
    }
}
