package com.aufdev.maratonapp;

import org.jasypt.util.password.BasicPasswordEncryptor;

/**
 * Created by Alejandro on 29/03/2016.
 */
public class PasswordEncryptor {
    private static PasswordEncryptor ourInstance = new PasswordEncryptor();
    private BasicPasswordEncryptor encryptor;

    private PasswordEncryptor() {
        encryptor = new BasicPasswordEncryptor();
    }

    public static PasswordEncryptor getInstance() {
        return ourInstance;
    }

    public String encrypt(String pass) {
        return encryptor.encryptPassword(pass);
    }

    public boolean check(String decrypted, String encrypted) {
        return encryptor.checkPassword(decrypted, encrypted);

    }
}
