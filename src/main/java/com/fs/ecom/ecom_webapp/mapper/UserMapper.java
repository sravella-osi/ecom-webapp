package com.fs.ecom.ecom_webapp.mapper;

import com.fs.ecom.ecom_webapp.dto.UserDetailsDTO;
import com.fs.ecom.ecom_webapp.models.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    public UserDetailsDTO getUserDetailsDTO(User user){
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();

        userDetailsDTO.setId(user.getId()) ;
        userDetailsDTO.setFirstName(user.getFirstName());
        userDetailsDTO.setLastName(user.getLastName());
        userDetailsDTO.setEmail(user.getEmail());
        userDetailsDTO.setMobile(user.getMobile());
        userDetailsDTO.setUserName(user.getUserName());

        return userDetailsDTO;
    }

    public User toUser(UserDetailsDTO userDetailsDTO){
        User user = new User();
        if(userDetailsDTO.getId()!=null) user.setId(userDetailsDTO.getId());
        user.setUserName(userDetailsDTO.getUserName());
        user.setEmail(userDetailsDTO.getEmail());
        user.setMobile(userDetailsDTO.getMobile());
        user.setFirstName(userDetailsDTO.getFirstName());
        user.setLastName(userDetailsDTO.getLastName());
        return user;
    }

    public List<UserDetailsDTO> getUserDetailsList(List<User> users){
        List<UserDetailsDTO> userDetails = new ArrayList<>();
        for(User user : users){
            userDetails.add(getUserDetailsDTO(user));
        }
        return userDetails;
    }
}
