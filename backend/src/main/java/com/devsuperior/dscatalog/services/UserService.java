package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.dto.RoleDTO;
import com.devsuperior.dscatalog.dto.UserDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.entities.Role;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.repositories.UserRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import org.hibernate.tool.schema.extract.spi.DatabaseInformation;
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
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public UserDTO findById(Long id){
        User user = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found"));
        return modelMapper.map(user, UserDTO.class);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(PageRequest pageRequest){
        Page<User> userPage = repository.findAll(pageRequest);
        return userPage.map(u -> modelMapper.map(u, UserDTO.class));
    }

    @Transactional
    public UserDTO update(Long id, UserDTO userDTO){
        User user = repository.getReferenceById(id);
        user = modelMapper.map(userDTO, User.class);
        return modelMapper.map(user, UserDTO.class);
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

    @Transactional
    public UserDTO insert(UserDTO userDTO){
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        for(RoleDTO roleDTO: userDTO.getRoles()){
            user.getRoles().add(modelMapper.map(roleDTO, Role.class));
        }
        user = repository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }
}
