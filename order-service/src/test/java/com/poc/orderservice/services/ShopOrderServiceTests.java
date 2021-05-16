package com.poc.orderservice.services;

import com.poc.orderservice.TestUtilities;
import com.poc.orderservice.models.ShopOrder;
import com.poc.orderservice.repositories.ShopOrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ShopOrderServiceTests {

    private final ShopOrderRepository shopOrderRepository = Mockito.mock(ShopOrderRepository.class);
    private final ShopOrderService service = new ShopOrderService(shopOrderRepository);
    private final TestUtilities util = new TestUtilities();

    @Test
    public void placeShopOrder_CallsRepository() {
        //arrange
        ShopOrder order = util.generateRandomShopOrder();

        //act
        service.placeShopOrder(order);

        //assert
        Mockito.verify(shopOrderRepository).insert(order);
    }
}
