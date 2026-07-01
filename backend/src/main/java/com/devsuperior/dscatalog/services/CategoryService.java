package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id){
        Category category =  repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resourse not found"));
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(PageRequest pageRequest){
        Page<Category> categories = repository.findAll(pageRequest);
        return categories.map(c -> modelMapper.map(c, CategoryDTO.class));
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO categoryDTO){
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category = repository.save(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        try{
            Category category = repository.getReferenceById(id);
            category.setName(dto.getName());
            category = repository.save(category);
            return modelMapper.map(category, CategoryDTO.class);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException("Resource not found");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Resrouce not found");
        }
        try{
            repository.deleteById(id);
        }catch(DataIntegrityViolationException e){
            throw new DatabaseException("Referential integrity failure");
        }
    }
}
