package com.devsuperior.dscatalog.tests.repositories;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.tests.Factory.ProductFactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    private Long existingId = 1L;
    private Long nonExistingId = 3000L;
    private Long countTotalProducts = 25L;

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull(){
        Product product = ProductFactory.createProduct();
        product.setId(null);

        product = repository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts + 1, product.getId());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){
        repository.deleteById(existingId);
        Optional<Product> result = repository.findById(existingId);
        Assertions.assertFalse(result.isPresent());
    }
    
    @Test
    public void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {
    	Optional<Product> result = repository.findById(existingId);
    	Assertions.assertTrue(result.isPresent());
    }
    
    @Test
    public void findByIdShouldReturnEmptyOptionalWhenIdNotExists() {
    	Optional<Product> result = repository.findById(nonExistingId);
    	Assertions.assertTrue(!result.isPresent());
    }
}
