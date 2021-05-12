package com.poc.productservice.controllers;

import com.poc.productservice.entities.Product;
import com.poc.productservice.services.ProductService;
import com.poc.productservice.util.TestUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class ProductControllerTests {

    private final TestUtilities util = new TestUtilities();
    private final ProductService productService = Mockito.mock(ProductService.class);
    private final ProductController productController = new ProductController(productService);

    @Test
    public void getAllProducts_CallsService_ReturnsResponseEntityContainingResponse() {
        //arrange
        List<Product> mockedAllProducts = new ArrayList<>();
        mockedAllProducts.add(util.getRandomProduct());
        var expectedResponse = new ResponseEntity<>(mockedAllProducts, HttpStatus.OK);
        Mockito.when(productService.getAllProducts()).thenReturn(mockedAllProducts);

        //act
        var response = productController.getAllProducts();

        //assert
        Assertions.assertEquals(response, expectedResponse);
        Mockito.verify(productService).getAllProducts();
    }

}
