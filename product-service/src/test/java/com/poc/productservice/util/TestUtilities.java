package com.poc.productservice.util;

import com.poc.productservice.entities.Product;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class TestUtilities {

    public TestUtilities() {
    }

    /**
     * Get a random String based on UUIDs.
     * @return A random String.
     */
    public String getRandomString() {
        return UUID.randomUUID().toString();
    }

    /**
     * Get a random Double.
     * @return A random Double between 1 and Double.MAX_VALUE.
     */
    public Double getRandomPositiveDouble() {
        return ThreadLocalRandom.current().nextDouble(1, Double.MAX_VALUE);
    }

    public Integer getRandomPositiveInteger() {
        return ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
    }

    public Long getRandomPositiveLong() {
        return ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE);
    }

    public Product getRandomProduct() {
        return new Product(
                getRandomString(),
                getRandomPositiveDouble(),
                getRandomPositiveInteger()
        );
    }

    public String getRandomStringOfLength(int length) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }
}
