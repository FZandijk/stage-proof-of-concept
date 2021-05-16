package com.poc.orderservice.controllers;

import com.poc.orderservice.TestUtilities;
import com.poc.orderservice.models.ShopOrder;
import com.poc.orderservice.services.ShopOrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class ShopOrderControllerUnitTests {

    private final ShopOrderService shopOrderService = Mockito.mock(ShopOrderService.class);
    private final ShopOrderController controller = new ShopOrderController(shopOrderService);
    private final TestUtilities util = new TestUtilities();

    @Test
    public void placeShopOrder_CallsService_ReturnsCreated() {
        //arrange
        ShopOrder order = util.generateRandomShopOrder();

        //act
        var response = controller.placeShopOrder(order);

        //assert
        verify(shopOrderService).placeShopOrder(order);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }
}
