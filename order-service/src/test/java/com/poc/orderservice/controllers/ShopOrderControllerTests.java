package com.poc.orderservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.orderservice.TestUtilities;
import com.poc.orderservice.models.ShopOrder;
import com.poc.orderservice.services.ShopOrderService;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class ShopOrderControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ShopOrderService shopOrderService;

    private final TestUtilities util = new TestUtilities();

    @Test
    public void placeShopOrder_CallsService_ReturnsCreated() throws Exception {
        //arrange
        String url = "/api/orders/place-order";
        ShopOrder toCreate = util.generateRandomShopOrder();

        //act + assert
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toCreate)))
                .andExpect(status().isCreated());
    }

    @Test
    public void placeShopOrder_ReturnsBadRequestAndErrorMessage_WhenCustomerNameIsBlank() throws Exception {
        //arrange
        String url = "/api/orders/place-order";
        ShopOrder toCreate = util.generateRandomShopOrder();
        toCreate.setCustomerName(Strings.EMPTY);

        //act + assert
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toCreate)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The customer name cannot be blank."));
    }

    @Test
    public void placeShopOrder_ReturnsBadRequestAndErrorMessage_WhenItemsIsEmpty() throws Exception {
        //arrange
        String url = "/api/orders/place-order";
        ShopOrder toCreate = util.generateRandomShopOrder();
        toCreate.setItems(new ArrayList<>());

        //act + assert
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toCreate)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Provide at least one item in the order."));
    }

    @Test
    public void placeShopOrder_ReturnsBadRequestAndErrorMessage_WhenItemsIsNull() throws Exception {
        //arrange
        String url = "/api/orders/place-order";
        ShopOrder toCreate = util.generateRandomShopOrder();
        toCreate.setItems(null);

        //act + assert
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toCreate)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Items cannot be null."));
    }

    @Test
    public void placeShopOrder_ReturnsBadRequestAndErrorMessage_WhenItemProductIdIsNull() throws Exception {
        //arrange
        String url = "/api/orders/place-order";
        ShopOrder toCreate = util.generateRandomShopOrder();
        toCreate.getItems().get(0).setProductId(null);

        //act + assert
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toCreate)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The productId cannot be null."));
    }

    @Test
    public void placeShopOrder_ReturnsBadRequestAndErrorMessage_WhenItemShortDescriptionIsLongerThan45Characters() throws Exception {
        //arrange
        String url = "/api/orders/place-order";
        ShopOrder toCreate = util.generateRandomShopOrder();
        toCreate.getItems().get(0).setShortDescription(util.getRandomStringOfLength(46));

        //act + assert
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toCreate)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The maximum length of a short description is 45 characters."));
    }

    @Test
    public void placeShopOrder_ReturnsBadRequestAndErrorMessage_WhenItemShortDescriptionIsBlank() throws Exception {
        //arrange
        String url = "/api/orders/place-order";
        ShopOrder toCreate = util.generateRandomShopOrder();
        toCreate.getItems().get(0).setShortDescription(Strings.EMPTY);

        //act + assert
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toCreate)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The short description cannot be blank."));
    }

    @Test
    public void placeShopOrder_ReturnsBadRequestAndErrorMessage_WhenCountIsZero() throws Exception {
        //arrange
        String url = "/api/orders/place-order";
        ShopOrder toCreate = util.generateRandomShopOrder();
        toCreate.getItems().get(0).setCount(0);

        //act + assert
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toCreate)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The count cannot be 0 or negative."));
    }

    @Test
    public void placeShopOrder_ReturnsBadRequestAndErrorMessage_WhenCountIsNegative() throws Exception {
        //arrange
        String url = "/api/orders/place-order";
        ShopOrder toCreate = util.generateRandomShopOrder();
        toCreate.getItems().get(0).setCount(util.getRandomPositiveInteger()*-1);

        //act + assert
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toCreate)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The count cannot be 0 or negative."));
    }

    @Test
    public void placeShopOrder_ReturnsBadRequestAndErrorMessage_WhenCountIsNull() throws Exception {
        //arrange
        String url = "/api/orders/place-order";
        ShopOrder toCreate = util.generateRandomShopOrder();
        toCreate.getItems().get(0).setCount(null);

        //act + assert
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toCreate)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The count cannot be null."));
    }

    @Test
    public void placeShopOrder_ReturnsBadRequestAndErrorMessage_WhenPriceIsZero() throws Exception {
        //arrange
        String url = "/api/orders/place-order";
        ShopOrder toCreate = util.generateRandomShopOrder();
        toCreate.getItems().get(0).setPrice(0.0);

        //act + assert
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toCreate)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The price cannot be 0 or negative."));
    }

    @Test
    public void placeShopOrder_ReturnsBadRequestAndErrorMessage_WhenPriceIsNegative() throws Exception {
        //arrange
        String url = "/api/orders/place-order";
        ShopOrder toCreate = util.generateRandomShopOrder();
        toCreate.getItems().get(0).setPrice(util.getRandomPositiveDouble()*-1);

        //act + assert
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toCreate)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The price cannot be 0 or negative."));
    }

    @Test
    public void placeShopOrder_ReturnsBadRequestAndErrorMessage_WhenPriceIsNull() throws Exception {
        //arrange
        String url = "/api/orders/place-order";
        ShopOrder toCreate = util.generateRandomShopOrder();
        toCreate.getItems().get(0).setPrice(null);

        //act + assert
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toCreate)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The price cannot be null."));
    }
}
