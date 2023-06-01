package com.electronicstore.service;

import com.electronicstore.dtos.UserDto;
import com.electronicstore.entity.Role;
import com.electronicstore.entity.User;
import com.electronicstore.repository.RoleRepository;
import com.electronicstore.repository.UserRepository;
import com.electronicstore.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Component;

import javax.annotation.meta.When;
import java.util.Optional;
import java.util.Set;


@SpringBootTest
public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper mapper;

    User user;

    Role role;


    String rollId;



    @Autowired
    private UserService userService;

    @BeforeEach
    public  void init(){

        role=Role.builder()
                .roleId("abc")
                .roleName("NORMAL").build();

       user = User.builder()
                .name("Ankur singh")
                .email("ankur@123")
                .about("testing create method ")
                .gender("male")
                .imageName("abc.png")
                .password("abcd")
                .roles(Set.of(role)).build();
       rollId="abc";

    }


    @Test
    public  void createUser(){
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        Mockito.when(roleRepository.findById(Mockito.anyString())).thenReturn(Optional.of(role));

        UserDto user1 = userService.crateUser(mapper.map(user, UserDto.class));
        System.out.println(user1.getName());
        Assertions.assertNotNull(user1);
        Assertions.assertEquals("Ankur singh",user1.getName());

    }

    public void updateUserTest(){
        String userId="";
        UserDto userDto=UserDto.builder()

    }

}
