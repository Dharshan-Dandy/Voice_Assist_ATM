package com.bank.atm.security;

public class Encrypt {
    private static String xorEncrypt(String input, String key) {
        StringBuilder encrypted = new StringBuilder();

        for(int i = 0; i < input.length(); ++i) {
            char originalChar = input.charAt(i);
            char keyChar = key.charAt(i % key.length());
            char encryptedChar = (char)(originalChar ^ keyChar);
            encrypted.append(encryptedChar);
        }

        return encrypted.toString();
    }
}
