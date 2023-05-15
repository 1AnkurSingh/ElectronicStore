package com.electronicstore.services.imp;

import com.electronicstore.dtos.UserDto;
import com.electronicstore.entity.User;
import com.electronicstore.repository.UserRepository;
import com.electronicstore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDto crateUser(UserDto userDto) {
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        User user=dtoToEntity(userDto);
        User savedUser = userRepository.save(user);
        UserDto newDto=entityToDto(savedUser);

        return newDto;
    }



    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Id Not Found!!"));
        user.setName(userDto.getName());
//      user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setImageName(userDto.getImageName());
        User upadtedUser = userRepository.save(user);

        return entityToDto(upadtedUser);
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Id Not Found!!"));
        userRepository.delete(user);

    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Id Not Found!!"));

        return entityToDto(user);
    }

    @Override
    public List<UserDto> getAllUser()
    {
        List<User> users = userRepository.findAll();
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public UserDto getUserByEmail(String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("Email Not Found Exception!!!!"));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> getUserByKeyWord(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> dtoUsers = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoUsers;
    }


    private User dtoToEntity(UserDto userDto) {
        User user = User.builder()
                .userId(userDto.getUserId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .about(userDto.getAbout())
                .imageName(userDto.getImageName())
                .gender(userDto.getGender()).build();

        return user;
    }
    private UserDto entityToDto(User savedUser) {
        UserDto build = UserDto.builder()
                .userId(savedUser.getUserId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .password(savedUser.getPassword())
                .about(savedUser.getAbout())
                .imageName(savedUser.getImageName())
                .gender(savedUser.getGender()).build();
        return build;
    }



}
