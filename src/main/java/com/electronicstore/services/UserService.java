package com.electronicstore.services;

import com.electronicstore.dtos.PageableResponse;
import com.electronicstore.dtos.UserDto;
import com.electronicstore.entity.User;

import java.util.List;
import java.util.Optional;

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

    PageableResponse<UserDto>getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);


    // get user by email

    UserDto getUserByEmail( String userEmail);


    // (search)get user by keyword

    List<UserDto>getUserByKeyWord(String keyword);

    Optional<User>findUserByEmailOptional(String email);
}
