package com.hana053.micropost.presentation.core.components.avatar;

import com.hana053.micropost.domain.User;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lombok.Value;

@Value
public class AvatarViewModel {

    private final User user;

    public String getUrl(int size) {
        return "https://secure.gravatar.com/avatar/" + md5(user.getEmail()) + "?s=" + size;
    }

    private String md5(String source) {
        String hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digested = md.digest(source.getBytes("UTF-8"));
            BigInteger bigInt = new BigInteger(1, digested);
            hash = bigInt.toString(16);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return hash;
    }
}
