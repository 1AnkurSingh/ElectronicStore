package com.electronicstore.Controller;

import com.electronicstore.dtos.ApiResponseMessage;
import com.electronicstore.dtos.UserDto;
import com.electronicstore.services.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserServiceImp userServiceImp;
    @PostMapping("/crate")
    public ResponseEntity<UserDto>createUser(@RequestBody UserDto userDto){
        UserDto createdUser = userServiceImp.crateUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // update
    @PutMapping("/update/{UserId}")
    public ResponseEntity<UserDto>updateUser(@RequestBody UserDto userDto, @PathVariable("UserId")String userId){
        UserDto userDto1 = userServiceImp.updateUser(userDto, userId);
        return new  ResponseEntity<>(userDto1,HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable("userId") String userId){
        userServiceImp.deleteUser(userId);

        ApiResponseMessage message = ApiResponseMessage.builder()
                .message("user Deleted Successfully!!")
                .success(true)
                .status(HttpStatus.OK).build();

        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @GetMapping("/getById/{userId}")
    public ResponseEntity<UserDto>getUserById(@PathVariable("userId")String userId)
    {
        return new ResponseEntity<>(userServiceImp.getUserById(userId),HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public  ResponseEntity<List<UserDto>>getAllUser(){
        return new ResponseEntity<>(userServiceImp.getAllUser(),HttpStatus.OK);
    }

    @GetMapping("/getByEmail/{userEmail}")
    public ResponseEntity<UserDto>getUserByEmail(@PathVariable("userEmail") String userEmail){
        return new ResponseEntity<>(userServiceImp.getUserByEmail(userEmail),HttpStatus.OK);
    }
    @GetMapping("/getBykeyword/{userKeyword}")
    public ResponseEntity<List<UserDto>>getUserByKeyword(@PathVariable("userKeyword") String userKeyWord){
        return new ResponseEntity<>(userServiceImp.getUserByKeyWord(userKeyWord),HttpStatus.OK);
    }

}
