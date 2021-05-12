package com.poc.productservice.services;

import com.poc.productservice.entities.Product;
import com.poc.productservice.errorHandling.CustomNotFoundException;
import com.poc.productservice.repositories.ProductRepository;
import com.poc.productservice.util.TestUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class ProductServiceTests {

    private final ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    private final ProductService service = new ProductService(productRepository);
    private final TestUtilities util = new TestUtilities();

    @Test
    public void getAllProducts_CallsRepository_ReturnsResponse() {
        //arrange
        List<Product> expectedResponse = new ArrayList<>();
        expectedResponse.add(util.getRandomProduct());
        Mockito.when(productRepository.findAll()).thenReturn(expectedResponse);

        //act
        var response = service.getAllProducts();

        //assert
        Assertions.assertEquals(response, expectedResponse);
        verify(productRepository).findAll();
    }

    @Test
    public void deleteProduct_CallsRepository() {
        //arrange
        Long productId = util.getRandomPositiveLong();

        //act
        service.deleteProduct(productId);

        //assert
        verify(productRepository).deleteById(productId);
    }

    @Test
    public void deleteProduct_ThrowsCustomNotFoundException_WhenRepositoryThrowsEmptyResultDataAccessException() {
        //arrange
        Long productId = util.getRandomPositiveLong();
        String expectedErrorMessage = MessageFormat.format("No product with id {0} was found.", productId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(productId);

        //act + assert
        Exception ex = Assertions.assertThrows(CustomNotFoundException.class, () -> service.deleteProduct(productId));
        Assertions.assertEquals(ex.getMessage(), expectedErrorMessage);
    }

    @Test
    public void updateProduct_CallsRepositoryWithProductAndReturnsResponse_WhenIdOfProductIsFoundInDatabase() {
        //arrange
        Product toUpdate = util.getRandomProduct();
        Mockito.when(productRepository.findById(toUpdate.getProductId())).thenReturn(java.util.Optional.of(toUpdate));
        Mockito.when(productRepository.save(toUpdate)).thenReturn(toUpdate);

        //act
        var response = service.updateProduct(toUpdate);

        //assert
        Assertions.assertEquals(response, toUpdate);
        verify(productRepository).save(toUpdate);
    }

    @Test
    public void updateProduct_ThrowsCustomNotFoundException_WhenIdOfProductISNotFountInDatabase() {
        //arrange
        Product toUpdate = util.getRandomProduct();
        Mockito.when(productRepository.findById(toUpdate.getProductId())).thenReturn(Optional.empty());

        //act
        Assertions.assertThrows(CustomNotFoundException.class, () -> service.updateProduct(toUpdate));
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    public void createProduct_CallsRepository() {
        //arrange
        Product toCreate = util.getRandomProduct();

        //act
        service.createProduct(toCreate);

        //assert
        verify(productRepository).save(toCreate);
    }
}
