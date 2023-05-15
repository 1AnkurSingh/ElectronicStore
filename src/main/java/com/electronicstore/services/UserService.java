package com.electronicstore.services;

import com.electronicstore.dtos.UserDto;
import com.electronicstore.entity.User;

import java.util.List;

public interface UserService {

    // create
    UserDto crateUser(UserDto userDto);

    // update

    UserDto updateUser(UserDto userDto, String userId);

    // delete

    void deleteUser(String userId);

    // get user by id

    UserDto getUserById(String userId);



    // get All user

    List<UserDto>getAllUser();


    // get user by email

    UserDto getUserByEmail( String userEmail);


    // (search)get user by keyword

    List<UserDto>getUserByKeyWord(String keyword);
}
