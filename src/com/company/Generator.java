package com.company;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Generator {
    public static String generateCommonLangPassword() {
        String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(2);
        String specialChar = RandomStringUtils.random(2, 33, 47, false, false);
        String totalChars = RandomStringUtils.randomAlphanumeric(2);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
                .concat(numbers)
                .concat(specialChar)
                .concat(totalChars);
        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        String password = pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        return password;
    }

    public static Integer generateInt(int from, int to) {
        Random random = new Random();
        return random.nextInt((to - from) + 1) + from;
    }

    public static Integer generateInt(int numDigits) {
        int minimum = (int) Math.pow(10, numDigits - 1); // minimum value with 2 digits is 10 (10^1)
        int maximum = (int) Math.pow(10, numDigits) - 1; // maximum value with 2 digits is 99 (10^2 - 1)
        Random random = new Random();
        return minimum + random.nextInt((maximum - minimum) + 1);
    }
}
