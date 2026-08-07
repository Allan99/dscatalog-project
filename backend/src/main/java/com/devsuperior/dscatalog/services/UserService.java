package com.devsuperior.dscatalog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.RoleDTO;
import com.devsuperior.dscatalog.dto.UserDTO;
import com.devsuperior.dscatalog.dto.UserInsertDTO;
import com.devsuperior.dscatalog.entities.Role;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.repositories.RoleRepository;
import com.devsuperior.dscatalog.repositories.UserRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public UserDTO findById(Long id){
        User user = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found"));
        return new UserDTO(user);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable){
        Page<User> userPage = repository.findAll(pageable);
        return userPage.map(u -> new UserDTO(u));
    }

    @Transactional
    public UserDTO update(Long id, UserDTO userDTO){
        User user = repository.getReferenceById(id);
        user = userDtoToUser(userDTO, user);
        return new UserDTO(user);
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
    public UserDTO insert(UserInsertDTO userInsertDTO){
        User user = new User();
        user = userDtoToUser(userInsertDTO, user);
        user.setPassword(passwordEncoder.encode(userInsertDTO.getPassword()));
        user = repository.save(user);
        return new UserDTO(user);
    }

    private User userDtoToUser(UserDTO source, User target){
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setEmail(source.getEmail());
        target.getRoles().clear();
        for(RoleDTO roleDTO: source.getRoles()){
            Role role = roleRepository.getReferenceById(roleDTO.getId());
            target.getRoles().add(role);
        }
        return target;
    }
}
