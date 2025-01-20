package com.fs.ecom.ecom_webapp.services;

import com.fs.ecom.ecom_webapp.dto.LoginDTO;
import com.fs.ecom.ecom_webapp.dto.RegisterDTO;
import com.fs.ecom.ecom_webapp.dto.UpdateDTO;
import com.fs.ecom.ecom_webapp.dto.UserDetailsDTO;
import com.fs.ecom.ecom_webapp.models.User;
import com.fs.ecom.ecom_webapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public User getUser (Long id) {
        return repository.getReferenceById(id);
    }

    public void postUser (User user) {
        repository.save(user);
    }

    public UserDetailsDTO updateUser (UpdateDTO updateDTO) throws NullPointerException {
        User existingUser = repository.getReferenceById(updateDTO.getId());
        existingUser.setFirstName(updateDTO.getFirstName());
        existingUser.setLastName(updateDTO.getLastName());
        existingUser.setUserName(updateDTO.getUserName());
        existingUser.setEmail(updateDTO.getEmail());
        existingUser.setMobile(updateDTO.getMobile());

        repository.save(existingUser);
        return new UserDetailsDTO(existingUser);
    }

    public void deleteUser (Long id){
        repository.deleteById(id);
    }

    public void deleteAll (){
        repository.deleteAll();
    }

    public void registerUser (RegisterDTO registerDTO) {
        String firstName = registerDTO.getFirstName();
        String lastName = registerDTO.getLastName();
        User user = new User(firstName, lastName, registerDTO.defaultUserName(), registerDTO.getEmail(), registerDTO.getPassword(), registerDTO.getMobile());
        repository.save(user);
    }


}
