package com.devsuperior.dscatalog.tests.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.ProductService;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository repository;
	
	private long existingId;
	private long nonExistingId;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		existingId = 1L;
		nonExistingId = 1000L;
		// Se o seu service usa repository.existsById(id)
	    Mockito.when(repository.existsById(existingId)).thenReturn(true);
	    Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);
	    
	    // Se o seu service apenas chama repository.deleteById(id) sem retornar nada
	    Mockito.doNothing().when(repository).deleteById(existingId);
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});		
	}
}