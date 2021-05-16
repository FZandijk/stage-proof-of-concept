package com.poc.orderservice;

import com.poc.orderservice.models.ShopOrder;
import com.poc.orderservice.models.ShopOrderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class TestUtilities {

    public TestUtilities() {
    }

    public ShopOrder generateRandomShopOrder() {
        ShopOrder shopOrder = new ShopOrder();
        shopOrder.setOrderId(UUID.randomUUID());
        shopOrder.setCustomerName(getRandomString());
        List<ShopOrderItem> items = new ArrayList<>();
        items.add(generateRandomShopOrderItem());
        shopOrder.setItems(items);
        return shopOrder;
    }

    public ShopOrderItem generateRandomShopOrderItem() {
        ShopOrderItem item = new ShopOrderItem();
        item.setShortDescription(getRandomString());
        item.setCount(getRandomPositiveInteger());
        item.setPrice(getRandomPositiveDouble());
        return item;
    }

    public String getRandomString() {
        return UUID.randomUUID().toString();
    }

    public Double getRandomPositiveDouble() {
        return ThreadLocalRandom.current().nextDouble(1, Double.MAX_VALUE);
    }

    public Integer getRandomPositiveInteger() {
        return ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
    }

    public Long getRandomPositiveLong() {
        return ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE);
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
