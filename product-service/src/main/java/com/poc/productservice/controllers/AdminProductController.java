package com.poc.productservice.controllers;

import com.poc.productservice.entities.Product;
import com.poc.productservice.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/api/products")
public class AdminProductController {

    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Deletes a Product from the database.
     * @param productId The id (long) of the Product that needs to be deleted.
     * @return NO_CONTENT
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Updates a product. Throws CustomNotFoundException if the Product parameter has an id that cannot be found.
     * @param productToUpdate The Product to update.
     * @return OK + the updated Product.
     */
    @PutMapping()
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid Product productToUpdate) {
        Product updated = productService.updateProduct(productToUpdate);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    /**
     * Creates a new Product.
     * @param productToCreate The Product to create.
     * @return CREATED
     */
    @PostMapping()
    public ResponseEntity<Void> createProduct(@RequestBody @Valid Product productToCreate) {
        productService.createProduct(productToCreate);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
