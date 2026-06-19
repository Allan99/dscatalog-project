package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Product product = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found"));
        return modelMapper.map(product, ProductDTO.class);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable){
        Page<Product> products = repository.findAll(pageable);
        return products.map(p -> modelMapper.map(p, ProductDTO.class));
    }

    @Transactional
    public ProductDTO insert(ProductDTO productDTO){
        Product product = new Product();
        dtoToProduct(productDTO, product);
        product = repository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO){
        Product product = repository.getReferenceById(id);
        dtoToProduct(productDTO, product);
        product = repository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id){
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Resource not found");
        }
        try{
            repository.deleteById(id);
        }catch(DataIntegrityViolationException e){
            throw new DatabaseException("Referential integrity failure");
        }
    }

    private void dtoToProduct(ProductDTO source, Product target){
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setImgUrl(source.getImgUrl());
        target.setPrice(source.getPrice());
        for(CategoryDTO categoryDTO: source.getCategories()){
            target.getCategories().add(modelMapper.map(categoryDTO, Category.class));
        }
    }
}
