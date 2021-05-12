package com.poc.productservice.services;

import com.poc.productservice.entities.Product;
import com.poc.productservice.errorHandling.CustomNotFoundException;
import com.poc.productservice.repositories.ProductRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

//    @Transactional
//    public void placeOrderFailing(OrderToCreate order) {
//        List<Product> products = checkStockOfProducts(order.getProductsInOrder());
//        productRepository.saveAll(products);
//
//        var response = contactOrderService.createOrderUnstable(order);
//        if (response.getStatusCode() == HttpStatus.REQUEST_TIMEOUT) {
//            //rollback stock updates
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//        }
//    }

//    public void placeOrderNormally(OrderToCreate order) {
//        List<Product> products = checkStockOfProducts(order.getProductsInOrder());
//        productRepository.saveAll(products);
//
//        var response = contactOrderService.createOrderNormally(order);
//        if (response.getStatusCode() != HttpStatus.CREATED) {
//            throw new RuntimeException();
//        }
//    }

//    private List<Product> checkStockOfProducts(List<ProductToOrder> productsToOrder) {
//        List<Product> productsToUpdate = new ArrayList<>();
//        for(ProductToOrder productToOrder : productsToOrder) {
//            //does productId exist
//            Product product = productRepository.findById(productToOrder.getProductId()).orElseThrow(EntityNotFoundException::new);
//            //is stock sufficient
//            if (product.getStockTotal() - productToOrder.getAmount() < 0) {
//                throw new LimitedStockException("Not enough stock.");
//            }
//            product.setStockTotal(product.getStockTotal() - productToOrder.getAmount());
//            productsToUpdate.add(product);
//        }
//        return productsToUpdate;
//    }

    /**
     * Get all the Product entities in the database.
     * @return List of all found products. Empty List if no Product entities are found.
     */
    @Transactional
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Deletes the Product with the given product id. Throws a CustomNotFoundException when no product with the given id is found.
     * @param productId The id (long) of the Product that needs to be deleted.
     */
    public void deleteProduct(long productId) {
        try {
            productRepository.deleteById(productId);
        } catch (EmptyResultDataAccessException ex) {
            throw new CustomNotFoundException(MessageFormat.format("No product with id {0} was found.", productId));
        }
    }

    /**
     * Updates a Product. Throws CustomNotFoundException if the id of the Product is not found in the database.
     * @param product The Product to update.
     * @return The updated Product.
     */
    public Product updateProduct(Product product) {
        productRepository.findById(product.getProductId()).orElseThrow(() ->
                new CustomNotFoundException(MessageFormat.format("No Product with id {0} found.", product.getProductId())));
        return productRepository.save(product);
    }

    /**
     * Creates a new Product in the database.
     * @param productToCreate The Product to create.
     */
    public void createProduct(Product productToCreate) {
        productRepository.save(productToCreate);
    }
}
