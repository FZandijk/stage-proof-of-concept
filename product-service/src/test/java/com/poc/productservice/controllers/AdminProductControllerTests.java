package com.poc.productservice.controllers;

import com.poc.productservice.entities.Product;
import com.poc.productservice.services.ProductService;
import com.poc.productservice.util.TestUtilities;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AdminProductControllerTests {

    private final ProductService productService = Mockito.mock(ProductService.class);
    private final AdminProductController controller = new AdminProductController(productService);
    private final TestUtilities util = new TestUtilities();

    @Test
    public void deleteProduct_CallsService_ReturnsNoContent() {
        //arrange
        Long productId = util.getRandomPositiveLong();

        //act
        var response = controller.deleteProduct(productId);

        //assert
        assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
        verify(productService).deleteProduct(productId);
    }

    @Test
    public void updateProduct_CallsService_ReturnsOkAndResponseInResponseEntityBody() {
        //arrange
        Product toUpdate = util.getRandomProduct();
        when(productService.updateProduct(toUpdate)).thenReturn(toUpdate);

        //act
        var response = controller.updateProduct(toUpdate);

        //assert
        verify(productService).updateProduct(toUpdate);
        assertEquals(toUpdate, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void createProduct_CallsService_ReturnsCreated() {
        //arrange
        Product toCreate = util.getRandomProduct();

        //act
        var response = controller.createProduct(toCreate);

        //assert
        verify(productService).createProduct(toCreate);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }
}
