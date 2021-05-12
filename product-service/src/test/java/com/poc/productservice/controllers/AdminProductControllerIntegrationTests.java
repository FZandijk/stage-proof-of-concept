package com.poc.productservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.productservice.entities.Product;
import com.poc.productservice.errorHandling.CustomNotFoundException;
import com.poc.productservice.services.ProductService;
import com.poc.productservice.util.TestUtilities;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class AdminProductControllerIntegrationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProductService productService;

    private final TestUtilities util = new TestUtilities();

    @Test
    public void deleteProduct_ReturnsNoContent_WhenServiceDoesNotThrowException() throws Exception {
        //arrange
        String url = "/admin/api/products/" + util.getRandomPositiveLong();

        //act + assert
        mvc.perform(delete(url)).andExpect(status().isNoContent());
    }

    @Test
    public void deleteProduct_ReturnsNotFoundAndErrorMessage_WhenServiceThrowsCustomNotFoundException() throws Exception {
        //arrange
        long productId = util.getRandomPositiveLong();
        String expectedErrorMessage = util.getRandomString();
        Mockito.doThrow(new CustomNotFoundException(expectedErrorMessage)).when(productService).deleteProduct(productId);
        String url = "/admin/api/products/" + productId;

        //act + assert
        mvc.perform(delete(url))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedErrorMessage));
    }

    @Test
    public void updateProduct_CallsService_ReturnsOkAndResponse() throws Exception {
        //arrange
        String url = "/admin/api/products";
        Product toUpdate = util.getRandomProduct();
        Product updated = util.getRandomProduct();
        when(productService.updateProduct(argThat((Product p) -> p.equals(toUpdate)))).thenReturn(updated);

        //act + assert
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toUpdate)))
                .andExpect(content().json(mapper.writeValueAsString(updated)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateProduct_ReturnsNotFoundAndErrorMessage_WhenServiceThrowsCustomNotFoundException() throws Exception {
        //arrange
        String url = "/admin/api/products";
        Product toUpdate = util.getRandomProduct();
        String expectedErrorMessage = util.getRandomString();
        when(productService.updateProduct(any(Product.class))).thenThrow(new CustomNotFoundException(expectedErrorMessage));

        //act + assert
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toUpdate)))
                .andExpect(content().string(expectedErrorMessage))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateProduct_ReturnsBadRequestAndErrorMessage_WhenDescriptionIsLongerThan450Characters() throws Exception {
        String url = "/admin/api/products";
        Product toUpdate = util.getRandomProduct();
        toUpdate.setDescription(util.getRandomStringOfLength(451));
        String expectedErrorMessage = "The maximum length of a description is 450 characters.";

        //act + assert
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toUpdate)))
                .andExpect(content().string(expectedErrorMessage))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateProduct_ReturnsBadRequestAndErrorMessage_WhenDescriptionIsBlank() throws Exception {
        String url = "/admin/api/products";
        Product toUpdate = util.getRandomProduct();
        toUpdate.setDescription("");
        String expectedErrorMessage = "The description cannot be blank.";

        //act + assert
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toUpdate)))
                .andExpect(content().string(expectedErrorMessage))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateProduct_ReturnsBadRequestAndErrorMessage_WhenPriceIsNegative() throws Exception {
        String url = "/admin/api/products";
        Product toUpdate = util.getRandomProduct();
        toUpdate.setPrice(util.getRandomPositiveDouble()*-1);
        String expectedErrorMessage = "The price cannot be 0 or negative.";

        //act + assert
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toUpdate)))
                .andExpect(content().string(expectedErrorMessage))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateProduct_ReturnsBadRequestAndErrorMessage_WhenPriceIsZero() throws Exception {
        String url = "/admin/api/products";
        Product toUpdate = util.getRandomProduct();
        toUpdate.setPrice(0.0);
        String expectedErrorMessage = "The price cannot be 0 or negative.";

        //act + assert
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toUpdate)))
                .andExpect(content().string(expectedErrorMessage))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateProduct_ReturnsBadRequestAndErrorMessage_WhenPriceNull() throws Exception {
        String url = "/admin/api/products";
        Product toUpdate = util.getRandomProduct();
        toUpdate.setPrice(null);
        String expectedErrorMessage = "The price cannot be null.";

        //act + assert
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toUpdate)))
                .andExpect(content().string(expectedErrorMessage))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateProduct_ReturnsBadRequestAndErrorMessage_WhenAmountInStockIsNegative() throws Exception {
        String url = "/admin/api/products";
        Product toUpdate = util.getRandomProduct();
        toUpdate.setAmountInStock(util.getRandomPositiveInteger()*-1);
        String expectedErrorMessage = "The amount in stock cannot be negative.";

        //act + assert
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toUpdate)))
                .andExpect(content().string(expectedErrorMessage))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateProduct_ReturnsBadRequestAndErrorMessage_WhenAmountInStockIsNull() throws Exception {
        String url = "/admin/api/products";
        Product toUpdate = util.getRandomProduct();
        toUpdate.setAmountInStock(null);
        String expectedErrorMessage = "The amount in stock cannot be null.";

        //act + assert
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toUpdate)))
                .andExpect(content().string(expectedErrorMessage))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createProduct_CallsService_ReturnsCreated() throws Exception {
        //arrange
        String url = "/admin/api/products";
        Product toCreate = util.getRandomProduct();

        //act + assert
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toCreate)))
                .andExpect(status().isCreated());
    }

    @Test
    public void createProduct_ReturnsBadRequestAndErrorMessage_WhenDescriptionIsLongerThan450Characters() throws Exception {
        String url = "/admin/api/products";
        Product toUpdate = util.getRandomProduct();
        toUpdate.setDescription(util.getRandomStringOfLength(451));
        String expectedErrorMessage = "The maximum length of a description is 450 characters.";

        //act + assert
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toUpdate)))
                .andExpect(content().string(expectedErrorMessage))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createProduct_ReturnsBadRequestAndErrorMessage_WhenDescriptionIsBlank() throws Exception {
        String url = "/admin/api/products";
        Product toUpdate = util.getRandomProduct();
        toUpdate.setDescription("");
        String expectedErrorMessage = "The description cannot be blank.";

        //act + assert
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toUpdate)))
                .andExpect(content().string(expectedErrorMessage))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createProduct_ReturnsBadRequestAndErrorMessage_WhenPriceIsZero() throws Exception {
        String url = "/admin/api/products";
        Product toUpdate = util.getRandomProduct();
        toUpdate.setPrice(0.0);
        String expectedErrorMessage = "The price cannot be 0 or negative.";

        //act + assert
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toUpdate)))
                .andExpect(content().string(expectedErrorMessage))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createProduct_ReturnsBadRequestAndErrorMessage_WhenPriceIsNegative() throws Exception {
        String url = "/admin/api/products";
        Product toUpdate = util.getRandomProduct();
        toUpdate.setPrice(util.getRandomPositiveDouble()*-1);
        String expectedErrorMessage = "The price cannot be 0 or negative.";

        //act + assert
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toUpdate)))
                .andExpect(content().string(expectedErrorMessage))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createProduct_ReturnsBadRequestAndErrorMessage_WhenPriceIsNull() throws Exception {
        String url = "/admin/api/products";
        Product toUpdate = util.getRandomProduct();
        toUpdate.setPrice(null);
        String expectedErrorMessage = "The price cannot be null.";

        //act + assert
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toUpdate)))
                .andExpect(content().string(expectedErrorMessage))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createProduct_ReturnsBadRequestAndErrorMessage_WhenAmountInStockNegative() throws Exception {
        String url = "/admin/api/products";
        Product toUpdate = util.getRandomProduct();
        toUpdate.setAmountInStock(util.getRandomPositiveInteger()*-1);
        String expectedErrorMessage = "The amount in stock cannot be negative.";

        //act + assert
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toUpdate)))
                .andExpect(content().string(expectedErrorMessage))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createProduct_ReturnsBadRequestAndErrorMessage_WhenAmountInStockIsNull() throws Exception {
        String url = "/admin/api/products";
        Product toUpdate = util.getRandomProduct();
        toUpdate.setAmountInStock(null);
        String expectedErrorMessage = "The amount in stock cannot be null.";

        //act + assert
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toUpdate)))
                .andExpect(content().string(expectedErrorMessage))
                .andExpect(status().isBadRequest());
    }
}
